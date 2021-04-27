package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.dispatcher.MissionDispatcher;
import com.epam.jwd.core_final.dispatcher.MissionPlanner;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.List;
import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        // todo
        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance;
        PropertyReaderUtil.loadProperties();
        NassaContext nassa = NassaContext.getInstance();
            nassa.init();
        List<FlightMission> missions = MissionPlanner.getInstance()
                .planeMissions(nassa.isSmartCrewCreating(), nassa.isUsingDijkstra());
        MissionDispatcher.getInstance().dispatchMission(missions);

        return applicationContextSupplier::get;
    }
}


