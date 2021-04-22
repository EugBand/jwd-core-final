package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ParseEntityContent;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParseCrewContent implements ParseEntityContent<CrewMember> {

    @Override
    public void parseEntity(File sourceFile, List<CrewMember> entities) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line = reader.readLine();
            EntityFactory<CrewMember> memberFactory = new CrewMemberFactory();
            while (line != null) {
                if (line.charAt(0) != '#') {
                    String[] rawCrewArray = line.split(";");
                    for (String rawMember : rawCrewArray) {
                        String[] rawParam = rawMember.split(",");
                        entities.add(memberFactory.create(
                                Role.resolveRoleById(Integer.parseInt(rawParam[0])),
                                Rank.resolveRankById(Integer.parseInt(rawParam[2]))));
                        entities.get(entities.size() - 1).setName(rawParam[1]);
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
