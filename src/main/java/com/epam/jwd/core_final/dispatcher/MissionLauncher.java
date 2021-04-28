package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;

import java.time.LocalTime;
import java.util.List;

import static com.epam.jwd.core_final.domain.MissionResult.COMPLETED;
import static com.epam.jwd.core_final.domain.MissionResult.FAILED;
import static com.epam.jwd.core_final.domain.MissionResult.IN_PROGRESS;
import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;
import static java.lang.Math.ceil;

public final class MissionLauncher extends MissionMaintainer {
    private static MissionLauncher instance;
    private final int delay = ApplicationProperties.getFileRefreshRate();

    private MissionLauncher() {
    }

    public static MissionLauncher getInstance() {
        if (instance == null) instance = new MissionLauncher();
        return instance;
    }

    String launch(FlightMission mission) {
        mission.setMissionResult(IN_PROGRESS);
        for (long i = 0; i < mission.getDistance(); i++) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.log(ERROR, "Can't interrupt thread", e);
            }
            if ((Math.random() * 100) > (ceil(nassa.getFailureProbability() / 40.0) + 97.5)) {
                return failure(mission, i);
            }
        }
        return complete(mission);
    }

    String failure(FlightMission mission, long days) {
        mission.setMissionResult(FAILED);
        changeMembersStatus(mission.getAssignedSpaceShip().getAssignedCrew(), "LOST", true);
        mission.setEndDate(mission.getStartDate().plusDays(days));
        String endData = nassa.getDateTimeFormat().format( mission.getStartDate().plusDays(days).atTime(LocalTime.now()));
        return String.format("%s R.I.P.! Mission %s failed after %d days", endData, mission.getName(), days);
    }

    String complete(FlightMission mission) {
        mission.setMissionResult(COMPLETED);
        planetService.setVisited(mission.getTo(), true);
        changeMembersStatus(mission.getAssignedSpaceShip().getAssignedCrew(), "READY", true);
        shipService.assignSpaceshipOnMission(mission.getAssignedSpaceShip(), false);
        String endData = nassa.getDateTimeFormat().format( mission.getEndDate().atTime(LocalTime.now()));
        return String.format("%s SUCCESS! Mission %s completed after %d days",
                endData, mission.getName(), mission.getDistance());
    }

    private void changeMembersStatus(List<CrewMember> members, String state, Boolean status){
        for (CrewMember member : members){
            switch (state){
                case "LOST":
                    crewService.assingLostStatus(member, status);
                    break;
                case "READY":
                    crewService.assignReadyStatus(member, status);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
