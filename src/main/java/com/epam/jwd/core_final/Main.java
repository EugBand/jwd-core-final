package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.SingletonFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvalidStateException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        PropertyReaderUtil.loadProperties();
        System.out.println(ApplicationProperties.getCrewFileName());

        CrewMember member = new CrewMemberFactory().create(Role.COMMANDER, Rank.FIRST_OFFICER);
        System.out.println(member.getRank());
        EntityFactory<CrewMember> factory = (EntityFactory<CrewMember>) SingletonFactory.getInstance(CrewMemberFactory.class);
        System.out.println(factory.create(Role.COMMANDER, Rank.FIRST_OFFICER).getRole());


        ApplicationContext nassa = new NassaContext();
        nassa.retrieveBaseEntityList(Planet.class);
        nassa.init();
//        Application.start();
    }
}
