package com.p3solutions.archon_report_utility.yaml_utils;

import com.p3solutions.archon_report_utility.exception.ReportGenerationException;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

import static com.p3solutions.archon_report_utility.constants.YamlConstants.*;


/**
 * Singleton class responsible for loading configuration settings from a YAML file.
 * The configuration file is expected to contain the following keys:
 * - location: The file path where reports are generated.
 * - reportName: The name of the report to be generated.
 * - imagePath: The file path for images used in reports.
 */

@Getter
@Setter
public class ConfigLoader {

    private static ConfigLoader instance;
    private String location;
    private String reportName;
    private String imagePath;

    private ConfigLoader() throws ReportGenerationException {
        loadConfig();
    }


    /**
     * Retrieves the singleton instance of the ConfigLoader
     * @return the single instance of ConfigLoader
     */

    public static ConfigLoader getInstance() throws ReportGenerationException {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    /**
     * Loads configuration data from the YAML file located in the classpath.
     * The configuration file must contain valid entries for location, reportName, and imagePath.
     * Throws IllegalArgumentException if the configuration file is not found.
     * Throws ReportGenerationException if the configuration file value is not found.
     */

    private void loadConfig() throws ReportGenerationException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(ERROR_MESSAGE);
            }
            Map<String, String> configData = yaml.load(inputStream);
            this.location = configData.get(LOCATION);
            this.reportName = configData.get(REPORT_NAME);
            this.imagePath = configData.get(IMAGE_PATH);
        } catch (Exception e) {
            throw new ReportGenerationException("Error loading configuration: " + e.getMessage());
        }
    }

}
