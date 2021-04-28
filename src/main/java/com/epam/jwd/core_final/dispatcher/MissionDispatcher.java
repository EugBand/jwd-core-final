package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.printer.impl.AppJSONFilePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.domain.MissionResult.FAILED;
import static com.epam.jwd.core_final.domain.MissionResult.IN_PROGRESS;
import static com.epam.jwd.core_final.domain.MissionResult.PLANNED;
import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;
import static java.lang.Math.ceil;

public final class MissionDispatcher extends MissionMaintainer {
    private static MissionDispatcher instance;

    private MissionDispatcher() {
    }

    public static MissionDispatcher getInstance() {
        if (instance == null) instance = new MissionDispatcher();
        return instance;
    }

    public void dispatchMission(List<FlightMission> missions) {
        List<FlightMission> readyMission = missions.stream()
                .filter(item -> item.getMissionResult().equals(PLANNED)).collect(Collectors.toList());
        List<Thread> threads = new ArrayList<>();
        for (FlightMission mission : readyMission) {
            Runnable launch = () -> {
                String result = MissionLauncher.getInstance().launch(mission);
                printer.print(AppJSONFilePrinter.getInstance(), result).print(result);
            };
            Thread thread = new Thread(launch);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.log(ERROR, "Error join to thread", e);
            }
        }
        printResult(missions);
    }

    public void dispatchMissionWithExecutor(List<FlightMission> missions) {
        List<FlightMission> readyMission = missions.stream()
                .filter(item -> item.getMissionResult().equals(PLANNED))
                .collect(Collectors.toList());
        readyMission.forEach(item -> item.setMissionResult(IN_PROGRESS));
        int interval = ApplicationProperties.getFileRefreshRate();
        int day = 0;
        service.schedule(() -> {
            for (int i = 0; i < readyMission.size(); i++) {
                if ((Math.random() * 100) > (ceil(nassa.getFailureProbability() / 40.0) + 97.5)) {
                    if (readyMission.get(i).getMissionResult().equals(IN_PROGRESS)) {
                        String result = MissionLauncher.getInstance().failure(readyMission.get(i), i);
                        readyMission.get(i).setMissionResult(FAILED);
                        printer.print(AppJSONFilePrinter.getInstance(), result).print(result);
                    }
                }
            }
            for (FlightMission mission : readyMission) {
                if (mission.getMissionResult().equals(IN_PROGRESS)) {
                    String result = MissionLauncher.getInstance().complete(mission);
                    printer.print(AppJSONFilePrinter.getInstance(), result).print(result);
                }
            }
            if (readyMission.stream().noneMatch(item -> item.getMissionResult().equals(IN_PROGRESS))) {
                printResult(missions);
                this.service.shutdown();
            }
        }, interval, TimeUnit.MILLISECONDS);
    }

    private void printResult(List<FlightMission> missions) {
        int discoveredPlanet = planets.size() - fetchExistNotVisitedPlanets().size() - 1;
        int lostShips = missions.size() - discoveredPlanet;
        String result = String.format
                ("We've discovered %d planets! But we've lost %d ships!", discoveredPlanet, lostShips);
        printer.printWaiting(200, 7, '.')
                .print(AppJSONFilePrinter.getInstance(), result).print(result);
    }
}
