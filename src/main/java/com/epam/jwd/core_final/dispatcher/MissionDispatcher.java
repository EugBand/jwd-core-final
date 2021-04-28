package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.printer.impl.AppJSONFilePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

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
                .filter(item -> item.getMissionResult().equals(MissionResult.PLANNED)).collect(Collectors.toList());
        List<Thread> threads = new ArrayList<>();
        for (FlightMission mission : readyMission) {
            Runnable launch = () -> {
                String result = MissionLauncher.getInstance().launch(mission);
                printer.print(AppJSONFilePrinter.getInstance(),result).print(result);
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
    private void printResult(List<FlightMission> missions){
        int discoveredPlanet = planets.size() - fetchExistNotVisitedPlanets().size() - 1;
        int lostShips = missions.size() - discoveredPlanet;
        String result = String.format
                ("We've discovered %d planets! But we've lost %d ships!", discoveredPlanet, lostShips);
        printer.printWaiting(200,7,'.')
                .print(AppJSONFilePrinter.getInstance(), result).print(result);
    }
}
