package com.epam.jwd.core_final.context.parser.impl;

import com.epam.jwd.core_final.context.parser.IEntityParser;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.FactorySingletonBuilder;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.service.PlanetService;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class PlanetParser implements IEntityParser<Planet> {
    private static Long id = 0L;

    private static PlanetParser instance;

    private PlanetParser() {

    }

    public static IEntityParser<Planet> getInstance() {
        if (instance == null) instance = new PlanetParser();
        return instance;
    }

    @Override
    public void parseEntity(File sourceFile, List<Planet> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            PlanetService planetService = PlanetServiceImpl.getInstance();
            EntityFactory<Planet> planetFactory = FactorySingletonBuilder.getInstance(PlanetFactory.class);
            long coordY = 0;
            while (line != null) {
                String[] rawSpaceArray = line.split(",");
                long coordX = 0;
                for (String point : rawSpaceArray) {
                    if (!"null".equals(point)) {
                        Planet planet = planetFactory.create(id++, point, coordX, coordY);
                        planetService.createPlanet(planet);
                    }
                    coordX++;
                }
                line = reader.readLine();
                coordY++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

