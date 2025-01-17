package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;

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
            if ((Math.random() * 100) > (ceil(nassa.getFailureProbability() / 40) + 97.5)) {
                return failure(mission, i);
            }
        }
        return complete(mission);
    }

    private String failure(FlightMission mission, long day) {
        mission.setMissionResult(FAILED);
        mission.setEndDate(mission.getStartDate().plusDays(day));
        return String.format("R.I.P.! Mission %s failed after %d days", mission.getName(), day);
    }

    private String complete(FlightMission mission) {
        mission.setMissionResult(COMPLETED);
        planetService.setVisited(mission.getTo(), true);
        shipService.assignSpaceshipOnMission(mission.getAssignedSpaceShip(), false);
        return String.format("SUCCESS! Mission %s completed after %d days", mission.getName(), mission.getDistance());
    }
}
