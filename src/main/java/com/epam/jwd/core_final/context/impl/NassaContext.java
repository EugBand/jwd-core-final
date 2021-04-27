package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.parser.impl.CrewParser;
import com.epam.jwd.core_final.context.parser.impl.PlanetParser;
import com.epam.jwd.core_final.context.parser.impl.SpaceshipParser;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.IllegalCollectionTypeException;
import com.epam.jwd.core_final.exception.InvalidPropertiesStateException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

// todo
public class NassaContext implements ApplicationContext {
    private static NassaContext instance;
    private int failureProbability;
    private boolean usingDijkstra;
    private boolean smartCrewCreating;
    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<Planet> planetMap = new ArrayList<>();
    private final Collection<FlightMission> missions = new ArrayList<>();

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (instance == null) instance = new NassaContext();
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "CrewMember":
                return (Collection<T>) Optional.of(crewMembers).orElse(new ArrayList<>());
            case "Spaceship":
                return (Collection<T>) Optional.of(spaceships).orElse(new ArrayList<>());
            case "Planet":
                return (Collection<T>) Optional.of(planetMap).orElse(new ArrayList<>());
            case "FlightMission":
                return (Collection<T>) Optional.of(missions).orElse(new ArrayList<>());
            default:
                try {
                    throw new IllegalCollectionTypeException("Wrong Collection Type:" + (tClass.getSimpleName()));
                } catch (IllegalCollectionTypeException e) {
                    IAppLogger logger = AppLogger.getInstance();
                    logger.log(ERROR, "Wrong Collection Type:" + tClass.getSimpleName(), e);
                }
        }
        return null;
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
        if (!(crewSource.exists() || spaceshipSource.exists() || planetSource.exists()))
            throw new InvalidPropertiesStateException("Input file(s) not found");

        List<CrewMember> crewMemberList = (List<CrewMember>) retrieveBaseEntityList(CrewMember.class);
        List<Planet> planetList = (List<Planet>) retrieveBaseEntityList(Planet.class);
        List<Spaceship> spaceshipsList = (List<Spaceship>) retrieveBaseEntityList(Spaceship.class);

        new CrewParser().parseEntity(crewSource, crewMemberList);
        new PlanetParser().parseEntity(planetSource, planetList);
        new SpaceshipParser().parseEntity(spaceshipSource, spaceshipsList);

        AppMenu.getInstance().getApplicationContext();
    }

    public int getFailureProbability() {
        return failureProbability;
    }

    void setFailureProbability(int failureProbability) {
        this.failureProbability = failureProbability;
    }

    public boolean isUsingDijkstra() {
        return usingDijkstra;
    }

    void setUsingDijkstra(boolean usingDijkstra) {
        this.usingDijkstra = usingDijkstra;
    }

    public boolean isSmartCrewCreating() {
        return smartCrewCreating;
    }

    void setSmartCrewCreating(boolean smartCrewCreating) {
        this.smartCrewCreating = smartCrewCreating;
    }
}
