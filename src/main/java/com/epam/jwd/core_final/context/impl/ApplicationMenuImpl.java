package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.printer.impl.AppConsolePrinter;
import com.epam.jwd.core_final.printer.impl.AppJSONFilePrinter;

import java.util.Objects;

public final class ApplicationMenuImpl implements ApplicationMenu {
    private static ApplicationMenu instance;
    private NassaContext nassa;
    private final AppConsolePrinter printer = (AppConsolePrinter) AppConsolePrinter.getInstance();

    private ApplicationMenuImpl() {
    }

    public static ApplicationMenu getInstance(){
        if (instance == null) instance = new ApplicationMenuImpl();
        return instance;
    }

    @Override
    public void getApplicationContext() {
        nassa = NassaContext.getInstance();
        this.initUserMissionData();
        this.printInitData();
    }

    private void initUserMissionData() {
        this.handleOutput("FUTURAMA 3021 EXPANSE INIT!", printer);
        this.handleOutput("Type flights accident probability " +
                "from \"1\" to \"99\" (e.c. 40 = 1% per 1 distance to lost ship)", printer);
        nassa.setFailureProbability((Integer) this.handleInput(Integer.class));
        this.handleOutput("Type \"true\" if you want to select only the nearest unvisited planets " +
                "or \"false\" and planets will be selected by chance", printer);
        nassa.setUsingDijkstra((Boolean) this.handleInput(Boolean.class));
        this.handleOutput("Type \"true\" if you want to use smart crews creating " +
                "or \"false\" and crews will be appointed by order", printer);
        nassa.setSmartCrewCreating((Boolean) this.handleInput(Boolean.class));
    }

    private void printInitData(){
        String crewMessage = "Crews ready! Quantity = " +
                Objects.requireNonNull(nassa.retrieveBaseEntityList(CrewMember.class)).size() + " person";
        String planetMessage = "Planets map ready! Quantity = " +
                Objects.requireNonNull(nassa.retrieveBaseEntityList(Planet.class)).size() + " planet";
        String shipMessage = "Spaceships also ready! Quantity = " +
                Objects.requireNonNull(nassa.retrieveBaseEntityList(Spaceship.class)).size() + " ships";
        this.handleOutput(crewMessage, printer.printWaiting(200, 7, '.')
                .print(AppJSONFilePrinter.getInstance(), crewMessage));
        this.handleOutput(planetMessage, printer.printWaiting(200, 7, '.')
                .print(AppJSONFilePrinter.getInstance(), planetMessage));
        this.handleOutput(shipMessage, printer.printWaiting(200, 7, '.')
                .print(AppJSONFilePrinter.getInstance(), shipMessage));
    }
}