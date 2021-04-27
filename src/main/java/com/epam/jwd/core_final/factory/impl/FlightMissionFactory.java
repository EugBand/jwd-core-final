package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;

// do the same for other entities
public final class FlightMissionFactory implements EntityFactory<FlightMission> {

    FlightMissionFactory() {
    }

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission((long) args[0], (String) args[1], (Spaceship) args[2], (LocalDate) args[3],
                (LocalDate) args[4], (Long) args[5], (MissionResult) args[6], (Planet) args[7], (Planet) args[8]);
    }
}
