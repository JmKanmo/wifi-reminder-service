package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigUtilTest {
    @Test
    public void yamlConfigTest() {
        assertNotNull(ConfigUtil.yamlConfig);

        assertNotNull(ConfigUtil.getApiConfig());
        assertNotNull(ConfigUtil.getApiConfig().getUrl());
        assertNotNull(ConfigUtil.getApiConfig().getKey());
        assertNotNull(ConfigUtil.getApiConfig().getName());
        assertNotNull(ConfigUtil.getApiConfig().getType());
        assertNotNull(ConfigUtil.getApiConfig().getEncoding());
//        System.out.println(ConfigUtil.getApiConfig().toString());

        assertNotNull(ConfigUtil.getWorkConfig());
        assertNotEquals(ConfigUtil.getWorkConfig().getWorkCount(), 0);
        assertNotEquals(ConfigUtil.getWorkConfig().getTimeUnit(),0);
    }
}