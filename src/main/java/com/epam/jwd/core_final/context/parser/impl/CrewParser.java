package com.epam.jwd.core_final.context.parser.impl;

import com.epam.jwd.core_final.context.parser.IEntityParser;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.entityexception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FactorySingletonBuilder;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

public final class CrewParser implements IEntityParser<CrewMember> {
    private static Long id = 0L;

    private static IEntityParser<CrewMember> instance;

    private CrewParser() {

    }

    public static IEntityParser<CrewMember> getInstance() {
        if (instance == null) instance = new CrewParser();
        return instance;
    }

    @Override
    public void parseEntity(File sourceFile, List<CrewMember> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<CrewMember> memberFactory = FactorySingletonBuilder.getInstance(CrewMemberFactory.class);
            CrewService crewService = CrewServiceImpl.getInstance();
            while (line != null) {
                if (line.charAt(0) != '#') {
                    String[] rawCrewArray = line.split(";");
                    for (String rawMember : rawCrewArray) {
                        String[] rawParam = rawMember.split(",");
                        CrewMember newMember = memberFactory.create(
                                id++, rawParam[1],
                                Role.resolveRoleById(Integer.parseInt(rawParam[0])),
                                Rank.resolveRankById(Integer.parseInt(rawParam[2])));
                        crewService.createCrewMember(newMember);
                    }
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
