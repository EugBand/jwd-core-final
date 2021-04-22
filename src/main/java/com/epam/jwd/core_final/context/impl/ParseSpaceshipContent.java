package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ParseEntityContent;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseSpaceshipContent implements ParseEntityContent<Spaceship> {

    @Override
    public void parseEntity(File sourceFile, List<Spaceship> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<Spaceship> spaceshipFactory = new SpaceshipFactory();
            while (line != null) {
                if (line.charAt(0) != '#') {
                    Map<Role, Short> shipCrewList = new HashMap<>();
                    String[] rawShipArray = line.split(";");
                    String rawCrew = rawShipArray[2].substring(1, rawShipArray[2].length() -1);
                    System.out.println(rawCrew);
                    String[] rawCrewArray = rawCrew.split(",");
                    for(String crew : rawCrewArray){
                       Role role = Role.resolveRoleById(Integer.parseInt(crew.split(":")[0]));
                       Short amount = Short.parseShort(crew.split(":")[1]);
                       shipCrewList.put(role, amount);
                    }
                    entities.add(spaceshipFactory.create(shipCrewList, Long.parseLong(rawShipArray[1])));
                    System.out.println(rawShipArray[0]);
                    System.out.println(entities.size() - 1);
                    System.out.println(entities.get(0));
                    entities.get(entities.size() - 1).setName(rawShipArray[0]);

                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

