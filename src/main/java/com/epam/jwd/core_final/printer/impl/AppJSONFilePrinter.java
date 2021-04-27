package com.epam.jwd.core_final.printer.impl;

import com.epam.jwd.core_final.printer.IAppPrinter;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;
import com.epam.jwd.core_final.util.JSONFileCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;


@SuppressWarnings("UnstableApiUsage")
public class AppJSONFilePrinter implements IAppPrinter {
    private static IAppPrinter instance;
    private static int counter;
    private final IAppLogger logger = AppLogger.getInstance();
    private final File JSONFile;


    private AppJSONFilePrinter() {
        JSONFile = JSONFileCreator.initJSONFile();
    }

    public static IAppPrinter getInstance() {
        if (instance == null) instance = new AppJSONFilePrinter();
        return instance;
    }

    @Override
    public void print(String message) {
        try {
            writeToFile(createJson(message));
        } catch (IOException e) {
            logger.log(ERROR, "Error create or write JSON", e);
        }
    }

    @Override
    public void print(String... messages) {
        for (String message : messages) {
            try {
                writeToFile(createJson(message));
            } catch (IOException e) {
                logger.log(ERROR, "Error create or write JSON", e);
            }
        }
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String message) {
        try {
            writeToFile(createJson(message));
        } catch (IOException e) {
            logger.log(ERROR, "Error create or write JSON", e);
        }
        return nextPrinter;
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String... messages) {
        for (String message : messages) {
            try {
                writeToFile(createJson(message));
            } catch (IOException e) {
                logger.log(ERROR, "Error create or write JSON", e);
            }
        }
        return nextPrinter;
    }

    private String createJson(String message) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("message" + counter++, message);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    private void writeToFile(String message) throws IOException {
        Files.append(message, JSONFile, Charsets.UTF_8);
    }
}
