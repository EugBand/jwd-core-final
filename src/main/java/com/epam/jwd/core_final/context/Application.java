package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.ApplicationMenuImpl;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.dispatcher.MissionDispatcher;
import com.epam.jwd.core_final.dispatcher.MissionPlanner;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;


public interface Application {
    IAppLogger logger = AppLogger.getInstance();

    static ApplicationMenu start() throws InvalidStateException {
        // todo
        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance;
        NassaContext nassa = NassaContext.getInstance();
        nassa.init();
        ApplicationMenuImpl.getInstance().getApplicationContext();
        ScheduledExecutorService service = refreshAfterInterval(nassa);
        List<FlightMission> missions = MissionPlanner.getInstance()
                .planeMissions(nassa.isSmartCrewCreating(), nassa.isUsingDijkstra());
        MissionDispatcher.getInstance().dispatchMissionWithExecutor(missions);
        service.shutdown();
        return applicationContextSupplier::get;
    }

    private static ScheduledExecutorService refreshAfterInterval(NassaContext nassa) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() -> {
            try {
                nassa.init();
            } catch (InvalidStateException e) {
                logger.log(ERROR, "Error initialization", e);
            }
        }, 0, ApplicationProperties.getFileRefreshRate(), TimeUnit.MILLISECONDS);
        return service;
    }
}


