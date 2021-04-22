package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ParseEntityContent;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParsePlanetContent implements ParseEntityContent<Planet> {

    @Override
    public void parseEntity(File sourceFile, List<Planet> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<Planet> planetFactory = new PlanetFactory();
            long coordY = 0;
            while (line != null) {
                String[] rawSpaceArray = line.split(",");
                long coordX = 0;
                for (String point : rawSpaceArray) {
                    if (!"null".equals(point)) {
                        entities.add(planetFactory.create(coordX, coordY));
                        entities.get(entities.size() - 1).setName(point);
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

