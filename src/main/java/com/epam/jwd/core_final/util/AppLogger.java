package com.epam.jwd.core_final.util;


import com.epam.jwd.core_final.util.enums.LogTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Arrays;

public class AppLogger implements IAppLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger("");
    private static IAppLogger instance;


    private AppLogger() {
    }

    public static IAppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    @Override
    public void log(LogTypes category, String message, Throwable throwable) {
        Level level = Level.valueOf(category.name());
        switch (level) {
            case INFO:
                LOGGER.info(message, throwable);
                break;
            case DEBUG:
                LOGGER.debug(message, throwable);
                break;
            case WARN:
                LOGGER.warn(message, throwable);
                break;
            case ERROR:
                LOGGER.error(message, throwable);
                break;
        }
    }

    @Override
    public void log(LogTypes category, String... messages) {
        Level level = Level.valueOf(category.name());
        switch (level) {
            case INFO:
                LOGGER.info(Arrays.toString(messages));
                break;
            case DEBUG:
                LOGGER.debug(Arrays.toString(messages));
                break;
            case WARN:
                LOGGER.warn(Arrays.toString(messages));
                break;
            case ERROR:
                LOGGER.error(Arrays.toString(messages));
                break;
        }
    }
}
