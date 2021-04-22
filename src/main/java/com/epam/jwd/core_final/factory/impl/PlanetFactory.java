package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.SingletonFactory;

// do the same for other entities
public class PlanetFactory implements EntityFactory<Planet> {

    @Override
    public Planet create(Object... args) {
        return new Planet((Long) args[0], (Long) args[0]);
    }

}
