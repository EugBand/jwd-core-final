package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

// do the same for other entities
public final class SpaceshipFactory implements EntityFactory<Spaceship> {

    @SuppressWarnings("unchecked")
    @Override
    public Spaceship create(Object... args) {
        return new Spaceship((long) args[0], (String) args[1], (Map<Role, Short>) args[2], (long) args[3]);
    }
}
