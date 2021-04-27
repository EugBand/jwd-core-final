package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

public class JSONFileCreator {
    private static IAppLogger logger = AppLogger.getInstance();

    private JSONFileCreator() {
    }

    public static File initJSONFile() {
        String base = "./src/main/resources/" + ApplicationProperties.getOutputRootDir() + File.separator;
        String fileName = ApplicationProperties.getMissionsFileName();
        Path filePath = Path.of(base, fileName);
        Path JSONFile = null;
        try {
            Files.deleteIfExists(filePath);
            JSONFile = Files.createFile(filePath);
        } catch (IOException e) {
            logger.log(ERROR, "Error working with JSON file");
        }
        return Optional.of(Objects.requireNonNull(JSONFile).toFile()).orElseThrow(RuntimeException::new);
    }
}
