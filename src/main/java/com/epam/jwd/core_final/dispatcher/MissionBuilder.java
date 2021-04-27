package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.criteria.PlanetCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.contextexception.PlanetNotFoundException;
import com.epam.jwd.core_final.exception.contextexception.ShipsNotFoundException;
import com.epam.jwd.core_final.factory.impl.FactorySingletonBuilder;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.printer.impl.AppJSONFilePrinter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.random;
import static java.lang.Math.round;

public final class MissionBuilder extends MissionMaintainer {
    private static MissionBuilder instance;

    private MissionBuilder() {
    }

    public static MissionBuilder getInstance() {
        if (instance == null) instance = new MissionBuilder();
        return instance;
    }

    FlightMission createMission(Planet startPlanet, Spaceship ship, boolean isSmartChoicePlanet)
            throws ShipsNotFoundException, PlanetNotFoundException {
        if (fetchExistCompletedShips().size() == 0) throw new PlanetNotFoundException("Planet not visited not found");
        if (fetchExistTargetPlanets().size() == 0) throw new ShipsNotFoundException("Free ships not found");
        planetService.setVisited(startPlanet, true);
        List<Planet> notVisitedPlanets = planetService.findAllPlanetsByCriteria(PlanetCriteria.isNotVisited(true));
        List<Planet> notAssignedPlanets = planetService.findAllPlanetsByCriteria(PlanetCriteria.isNotAssigned(true));
        List<Planet> availablePlanets = notVisitedPlanets.stream()
                .filter(notAssignedPlanets::contains).collect(Collectors.toList());
        Planet to = getAvailablePlanet(startPlanet, availablePlanets, isSmartChoicePlanet);
        long distance = planetService.getDistanceBetweenPlanets(startPlanet, to);
        String missionName = "from-\"" + planetService.getName(startPlanet)
                + "\"-to-\"" + planetService.getName(to) + "\"-ship-\"" + ship.getName() + "\"-distance-" + distance;
        MissionResult missionResult = MissionResult.PLANNED;
        LocalDate startData = LocalDate.now().plusYears(1000);
        LocalDate endData = startData.plusDays(distance);
        planetService.setAssigned(to, true);
        shipService.assignSpaceshipOnMission(ship, true);
        FlightMissionFactory missionFactory = FactorySingletonBuilder.getInstance(FlightMissionFactory.class);
        FlightMission mission = missionFactory
                .create(System.currentTimeMillis(), missionName, ship, startData, endData, distance, missionResult, startPlanet, to);
        String result = "Mission " + mission.getName() + " created";
        printer.printWaiting(200,3, '.')
                .print(AppJSONFilePrinter.getInstance(),result).print(result);
        return mission;
    }

    private Planet getAvailablePlanet(Planet basePlanet, List<Planet> availablePlanets, boolean isSmart) {
        if (isSmart) {
            List<Long> distances = availablePlanets.stream()
                    .map(item -> planetService.getDistanceBetweenPlanets(basePlanet, item)).collect(Collectors.toList());
            Long minDistance = distances.stream().min(Comparator.comparing(Long::valueOf))
                    .orElseThrow(IllegalStateException::new);
            return availablePlanets.get(distances.indexOf(minDistance));
        }
        return availablePlanets.get((int) round(random() * (availablePlanets.size() - 1)));
    }
}
