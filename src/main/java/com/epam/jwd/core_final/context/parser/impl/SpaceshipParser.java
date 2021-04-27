package com.epam.jwd.core_final.context.parser.impl;

import com.epam.jwd.core_final.context.parser.IEntityParser;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.entityexception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.FactorySingletonBuilder;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class SpaceshipParser implements IEntityParser<Spaceship> {
    private static Long id = 0L;

    @Override
    public void parseEntity(File sourceFile, List<Spaceship> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<Spaceship> spaceshipFactory = FactorySingletonBuilder.getInstance(SpaceshipFactory.class);
            while (line != null) {
                if (line.charAt(0) != '#') {
                    Map<Role, Short> shipCrewList = new HashMap<>();
                    String[] rawShipArray = line.split(";");
                    String rawCrew = rawShipArray[2].substring(1, rawShipArray[2].length() -1);
                    String[] rawCrewArray = rawCrew.split(",");
                    for(String crew : rawCrewArray){
                       Role role = Role.resolveRoleById(Integer.parseInt(crew.split(":")[0]));
                       Short amount = Short.parseShort(crew.split(":")[1]);
                       shipCrewList.put(role, floor(amount/3) == 0 ? 1 : (short) (ceil(amount/3)));
                    }
                    Spaceship ship = spaceshipFactory.create(id++, rawShipArray[0],
                            shipCrewList, Long.parseLong(rawShipArray[1]));
                    entities.add(ship);

                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            logger.log(ERROR, "Error parsing input file", e);
        } catch (UnknownEntityException e) {
            logger.log(ERROR, "Error resolving Role or Rank", e);
        }
    }
}

