package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import logger.LoggingController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;

public class YamlParser {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    private static InputStream configInputStream = YamlParser.class.getClassLoader().getResourceAsStream("config.yaml");


    private YamlParser() {
    }

    public static <T> Optional<T> getParsedCrawlerConfig(Class<T> crawlerClz) {
        try {
            return Optional.ofNullable(objectMapper.readValue(configInputStream, crawlerClz));
        } catch (IOException e) {
            LoggingController.log(Level.INFO, "YamlParser:getParsedCrawlerConfig error occur => " + e);
        }
        return Optional.empty();
    }
}
