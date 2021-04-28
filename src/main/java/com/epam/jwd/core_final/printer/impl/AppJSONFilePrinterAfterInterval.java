package com.epam.jwd.core_final.printer.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.printer.IAppPrinter;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;
import com.epam.jwd.core_final.util.JSONFileCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

@SuppressWarnings("unused")
public class AppJSONFilePrinterAfterInterval implements IAppPrinter {
    private static IAppPrinter instance;
    private final IAppLogger logger = AppLogger.getInstance();
    private final File JSONFile;
    private StringBuilder accumulator;

    private AppJSONFilePrinterAfterInterval() {
        JSONFile = JSONFileCreator.initJSONFile();
        invokeAfterInterval();
    }

    public static IAppPrinter getInstance() {
        if (instance == null) instance = new AppJSONFilePrinterAfterInterval();
        return instance;
    }

    @Override
    public void print(String message) {
        try {
            accumulator.append(createJson(message));
        } catch (IOException e) {
            logger.log(ERROR, "Error create JSON", e);
        }
    }

    @Override
    public void print(String... messages) {
        for (String message : messages) {
            try {
                accumulator.append(createJson(message));
            } catch (IOException e) {
                logger.log(ERROR, "Error create JSON", e);
            }
        }
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String message) {
        try {
            accumulator.append(createJson(message));
        } catch (IOException e) {
            logger.log(ERROR, "Error create JSON", e);
        }
        return nextPrinter;
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String... messages) {
        for (String message : messages) {
            try {
                accumulator.append(createJson(message));
            } catch (IOException e) {
                logger.log(ERROR, "Error create JSON", e);
            }
        }
        return nextPrinter;
    }

    private void invokeAfterInterval() {
        int interval = ApplicationProperties.getFileRefreshRate();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.schedule(() -> {
            try {
                writeToFile(accumulator.toString());
            } catch (IOException e) {
                logger.log(ERROR, "Error of writing JSON to File", e);
            }
        }, interval, TimeUnit.MILLISECONDS);
    }


    private String createJson(String... messages) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            ObjectNode node = mapper.createObjectNode();
            node.put(String.valueOf(System.currentTimeMillis()), message);
            sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
        }
        return sb.toString();
    }

    private void writeToFile(String messages) throws IOException {
        try (FileWriter fw = new FileWriter(JSONFile)) {
            fw.write(messages);
        }
    }
}
