package util;

import config.YamlConfig;
import config.dto.Api;
import config.YamlParser;
import config.dto.Db;
import config.dto.Work;

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
}
