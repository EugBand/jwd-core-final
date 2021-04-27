package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.annotation.ISingleton;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.PlanetService;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;
import static java.lang.Math.random;
import static java.lang.Math.round;

@SuppressWarnings("all")
@ISingleton
public final class PlanetServiceImpl implements PlanetService {
    private static PlanetService instance;
    private List<Planet> planets = Optional.ofNullable((List<Planet>) NassaContext.getInstance()
            .retrieveBaseEntityList(Planet.class)).orElseThrow(IllegalStateException::new);

    private PlanetServiceImpl() {

    }

    public static PlanetService getInstance() {
        if (instance == null) {
            instance = new PlanetServiceImpl();
        }
        return instance;
    }

    @Override
    public Planet getRandomPlanet() {
        return planets.get((int) round(random() * planets.size()));
    }

    @Override
    public List<Planet> findAllPlanetsByCriteria(Predicate<? extends Planet> criteria) {
        return planets.stream()
                .filter((Predicate<? super Planet>) criteria).collect(Collectors.<Planet>toList());
    }

    @Override
    public Optional<Planet> findPlanetByCriteria(Predicate<? extends Planet> criteria) {
        return planets.stream()
                .filter((Predicate<? super Planet>) criteria).findFirst();
    }

    @Override
    public void setVisited(Planet planet, boolean visited) {
        planet.setVisited(visited);
    }

    @Override
    public boolean isVisited(Planet planet) {
        return planet.isVisited();
    }

    @Override
    public void setAssigned(Planet planet, boolean assigned) {
        planet.setAssigned(assigned);
    }

    @Override
    public boolean isAssigned(Planet planet) {
        return planet.isAssigned();
    }

    @Override
    public String getName(Planet planet) {
        return planet.getName();
    }

    @Override
    public long getDistanceBetweenPlanets(Planet first, Planet second) {
        long katetX = abs(first.getPoint().getX() - second.getPoint().getX());
        long katetY = abs(first.getPoint().getY() - second.getPoint().getY());
        return (int) hypot(katetX, katetY);
    }

}
