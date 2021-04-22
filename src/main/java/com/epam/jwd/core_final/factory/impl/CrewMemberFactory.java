package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.SingletonFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) {
        return new CrewMember((Role) args[0], (Rank) args[1]);
            }
}
