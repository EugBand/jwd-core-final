package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<Planet> planetMap = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        switch (tClass.getSimpleName()){
            case "CrewMember":
                return (Collection<T>) crewMembers;
            case "Spaceship":
                return (Collection<T>) spaceships;
            case "Planet":
                return (Collection<T>) planetMap;
            default: throw new IllegalStateException();
        }
    }

    /**
     * You have to read input files, populate collections
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        File crewSource = new File(ApplicationProperties.getCrewFileName());
        File spaceshipSource = new File(ApplicationProperties.getSpaceshipsFileName());
        File planetSource = new File(ApplicationProperties.getPlanetFileName());

        throw new InvalidStateException();
    }
}
