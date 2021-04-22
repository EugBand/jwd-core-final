package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<Planet> planetMap = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "CrewMember":
                return (Collection<T>) crewMembers;
            case "Spaceship":
                return (Collection<T>) spaceships;
            case "Planet":
                return (Collection<T>) planetMap;
            default:
                throw new IllegalStateException();
        }
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {

        String rootInputPath = "./src/main/resources/" + ApplicationProperties.getInputRootDir() + File.separator;
        File crewSource = new File(rootInputPath + ApplicationProperties.getCrewFileName());
        File spaceshipSource = new File(rootInputPath + ApplicationProperties.getSpaceshipsFileName());
        File planetSource = new File(rootInputPath + ApplicationProperties.getPlanetFileName());

        List<CrewMember> crewMemberList = (List<CrewMember>) retrieveBaseEntityList(CrewMember.class);
        List<Planet> planetList = (List<Planet>) retrieveBaseEntityList(Planet.class);
        List<Spaceship> spaceshipsList = (List<Spaceship>) retrieveBaseEntityList(Spaceship.class);

        new ParseCrewContent().parseEntity(crewSource, crewMemberList);
        new ParsePlanetContent().parseEntity(planetSource, planetList);
        new ParseSpaceshipContent().parseEntity(spaceshipSource, spaceshipsList);
        System.out.println(crewMemberList.size());
        System.out.println(crewMemberList.get(0).getName());
        System.out.println(planetList.size());
        System.out.println(planetList.get(0).getName());
        System.out.println(spaceshipsList.size());
        System.out.println(spaceshipsList.get(0).getName());
//        throw new InvalidStateException();
    }
}
