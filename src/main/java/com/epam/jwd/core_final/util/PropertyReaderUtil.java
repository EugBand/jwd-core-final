package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReaderUtil {

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "./src/main/resources/application.properties";
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFileName)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApplicationProperties.initProps(
                props.getProperty("inputRootDir"),
                props.getProperty("outputRootDir"),
                props.getProperty("crewFileName"),
                props.getProperty("missionsFileName"),
                props.getProperty("planetFileName"),
                props.getProperty("spaceshipsFileName"),
                Integer.valueOf(props.getProperty("fileRefreshRate")),
                props.getProperty("dateTimeFormat"));
    }
}
