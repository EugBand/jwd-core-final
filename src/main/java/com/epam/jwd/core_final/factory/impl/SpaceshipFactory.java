package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.SingletonFactory;

// do the same for other entities
public class SpaceshipFactory extends SingletonFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) {
        return null;
    }
}
