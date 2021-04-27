package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public final class PlanetFactory implements EntityFactory<Planet> {

    PlanetFactory() {
    }

    @Override
    public Planet create(Object... args) {
        return new Planet((long) args[0], (String) args[1], (long) args[2], (long) args[3]);
    }

}
