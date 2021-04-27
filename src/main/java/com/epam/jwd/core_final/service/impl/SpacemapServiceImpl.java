package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.annotation.ISingleton;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;
import static java.lang.Math.random;
import static java.lang.Math.round;

@SuppressWarnings("all")
@ISingleton
public final class SpacemapServiceImpl implements SpacemapService {
    private static SpacemapService instance;
    private List<Planet> planets = Optional.ofNullable((List<Planet>) NassaContext.getInstance()
            .retrieveBaseEntityList(Planet.class)).orElseThrow(IllegalStateException::new);

    private SpacemapServiceImpl() {

    }

    public static SpacemapService getInstance() {
        if (instance == null) {
            instance = new SpacemapServiceImpl();
        }
        return instance;
    }

    @Override
    public Planet getRandomPlanet() {
        return planets.get((int) round(random() * planets.size()));
    }

    @Override
    public long getDistanceBetweenPlanets(Planet first, Planet second) {
        long katetX = abs(first.getPoint().getX() - second.getPoint().getX());
        long katetY = abs(first.getPoint().getY() - second.getPoint().getY());
        return (int) hypot(katetX, katetY);
    }
}
