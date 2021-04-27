package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface PlanetService {

    Planet getRandomPlanet();

    // Dijkstra ?
    long getDistanceBetweenPlanets(Planet first, Planet second);

    List<Planet> findAllPlanetsByCriteria(Predicate<? extends Planet> criteria);

    Optional<Planet> findPlanetByCriteria(Predicate<? extends Planet> criteria);


    void setVisited(Planet planet, boolean visited);

    boolean isVisited(Planet planet);

    void setAssigned(Planet planet, boolean assigned);

    boolean isAssigned(Planet planet);

    String getName(Planet planet);
}
