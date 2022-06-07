package util;

import config.YamlConfig;
import config.dto.*;
import config.YamlParser;

public class ConfigUtil {
    public static final YamlConfig yamlConfig = YamlParser.getParsedCrawlerConfig(YamlConfig.class).get();

    public static Api getApiConfig() {
        return yamlConfig.getApi();
    }

    public static Work getWorkConfig() {
        return yamlConfig.getWork();
    }

    public static Db getDbConfig() {
        return yamlConfig.getDb();
    }

    public static DbPool getDbPoolConfig() {
        return yamlConfig.getDbPool();
    }

    public static Logger getLoggerConfig() {
        return yamlConfig.getLogger();
    }
}
