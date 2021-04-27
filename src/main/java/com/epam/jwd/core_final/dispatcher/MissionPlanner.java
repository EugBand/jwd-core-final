package com.epam.jwd.core_final.dispatcher;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.contextexception.CrewNotAssignException;
import com.epam.jwd.core_final.exception.contextexception.PlanetNotFoundException;
import com.epam.jwd.core_final.exception.contextexception.ShipsNotFoundException;
import com.epam.jwd.core_final.printer.impl.AppJSONFilePrinter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

public final class MissionPlanner extends MissionMaintainer {
    private static MissionPlanner instance;

    private MissionPlanner() {
    }

    public static MissionPlanner getInstance() {
        if (instance == null) instance = new MissionPlanner();
        return instance;
    }

    @SneakyThrows
    public List<FlightMission> planeMissions(boolean isSmartCrew, boolean isSmartChoicePlanet) {
        List<FlightMission> missions = new ArrayList<>();
        List<Spaceship> ships = MissionCompleter.getInstance().shipComplete(isSmartCrew);
        if (ships.size() == 0) throw new CrewNotAssignException("Error assign crews to ships");
        String result = ships.size() + " has been complected!";
        printer.printWaiting(200, 7, '.').print(AppJSONFilePrinter.getInstance(), result
        ).print(result);
        for (Spaceship ship : ships) {
            try {
                missions.add(MissionBuilder.getInstance().createMission(planets.get(0), ship, isSmartChoicePlanet));
            } catch (ShipsNotFoundException e) {
                logger.log(ERROR, "(Error seeking ship", e);
            } catch (PlanetNotFoundException e) {
                logger.log(ERROR, "(Error seeking planer", e);
            }
        }
        return missions;
    }
}