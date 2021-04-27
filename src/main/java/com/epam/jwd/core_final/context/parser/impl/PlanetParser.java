package com.epam.jwd.core_final.context.parser.impl;

import com.epam.jwd.core_final.context.parser.IEntityParser;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.FactorySingletonBuilder;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PlanetParser implements IEntityParser<Planet> {
    private static Long id = 0L;

    @Override
    public void parseEntity(File sourceFile, List<Planet> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<Planet> planetFactory = FactorySingletonBuilder.getInstance(PlanetFactory.class);
            long coordY = 0;
            while (line != null) {
                String[] rawSpaceArray = line.split(",");
                long coordX = 0;
                for (String point : rawSpaceArray) {
                    if (!"null".equals(point)) {
                        Planet planet = planetFactory.create(id++, point, coordX, coordY);
                        entities.add(planet);
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

