package com.epam.jwd.core_final.domain;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */


public final class ApplicationProperties {
    //todo
    private static boolean isInit = false;
    private static String inputRootDir;
    private static String outputRootDir;
    private static String crewFileName;
    private static String missionsFileName;
    private static String planetFileName;
    private static String spaceshipsFileName;
    private static Integer fileRefreshRate;
    private static String dateTimeFormat;

    private ApplicationProperties() {
    }

    public static void initProps(String inputRootDir, String outputRootDir, String crewFileName,
                                 String missionsFileName, String planetFileName, String spaceshipsFileName,
                                 Integer fileRefreshRate, String dateTimeFormat) {
        if (!isInit) {
            isInit = true;
            ApplicationProperties.inputRootDir = inputRootDir;
            ApplicationProperties.outputRootDir = outputRootDir;
            ApplicationProperties.crewFileName = crewFileName;
            ApplicationProperties.missionsFileName = missionsFileName;
            ApplicationProperties.planetFileName = planetFileName;
            ApplicationProperties.spaceshipsFileName = spaceshipsFileName;
            ApplicationProperties.fileRefreshRate = fileRefreshRate;
            ApplicationProperties.dateTimeFormat = dateTimeFormat;
        }
    }

    public static String getInputRootDir() {
        return inputRootDir;
    }

    public static String getOutputRootDir() {
        return outputRootDir;
    }

    public static String getCrewFileName() {
        return crewFileName;
    }

    public static String getMissionsFileName() {
        return missionsFileName;
    }

    public static String getPlanetFileName() {
        return planetFileName;
    }

    public static String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public static Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public static String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
