package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.PlanetCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.printer.impl.AppConsolePrinter;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.PlanetService;
import com.epam.jwd.core_final.service.ShipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;
import com.epam.jwd.core_final.service.impl.ShipServiceImpl;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

abstract class MissionMaintainer {
    final AppConsolePrinter printer = (AppConsolePrinter) AppConsolePrinter.getInstance();
    NassaContext nassa = NassaContext.getInstance();
    IAppLogger logger = AppLogger.getInstance();
    List<Planet> planets;
    List<Spaceship> ships;
    PlanetService planetService = PlanetServiceImpl.getInstance();
    ShipService shipService = ShipServiceImpl.getInstance();
    CrewService crewService = CrewServiceImpl.getInstance();
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    {
        planets = Optional.ofNullable((List<Planet>) nassa.retrieveBaseEntityList(Planet.class))
                .orElseThrow(IllegalStateException::new);
        ships = Optional.ofNullable((List<Spaceship>) nassa.retrieveBaseEntityList(Spaceship.class))
                .orElseThrow(IllegalStateException::new);
    }

    List<Planet> fetchExistTargetPlanets() {
        List<Planet> notVisitedPlanets = planetService.findAllPlanetsByCriteria(PlanetCriteria.isNotVisited(true));
        List<Planet> notAssignedPlanets = planetService.findAllPlanetsByCriteria(PlanetCriteria.isNotAssigned(true));
        return notVisitedPlanets.stream()
                .filter(notAssignedPlanets::contains).collect(Collectors.toList());
    }

    List<Planet> fetchExistNotVisitedPlanets() {
        return planetService.findAllPlanetsByCriteria(PlanetCriteria.isNotVisited(true));
    }

    List<Spaceship> fetchExistCompletedShips() {
        return shipService.findAllSpaceshipsByCriteria(SpaceshipCriteria.isNotAssigned(true));
    }
}
