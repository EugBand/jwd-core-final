package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.SingletonFactory;

import java.time.LocalDate;

// do the same for other entities
public class FlightMissionFactory implements EntityFactory<FlightMission> {

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission((String) args[0], (LocalDate) args[1], (LocalDate) args[2], (Long) args[3],
                (MissionResult) args[4], (Planet) args[5], (Planet) args[6]);
    }
}
