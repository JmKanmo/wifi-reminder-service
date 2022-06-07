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
        assertNotEquals(ConfigUtil.getWorkConfig().getTimeUnit(), 0);

        assertNotNull(ConfigUtil.getDbConfig());
        assertNotNull(ConfigUtil.getDbConfig().getName());
        assertNotNull(ConfigUtil.getDbConfig().getUrl());
        assertNotNull(ConfigUtil.getDbConfig().getPassword());
        assertNotNull(ConfigUtil.getDbConfig().getUser());

        assertNotNull(ConfigUtil.getDbPoolConfig());
        assertNotNull(ConfigUtil.getDbPoolConfig().getPoolName());
        assertNotNull(ConfigUtil.getDbPoolConfig().getMaxPoolSize());
        assertNotNull(ConfigUtil.getDbPoolConfig().getMinPoolSize());
        assertNotNull(ConfigUtil.getDbPoolConfig().getPoolValidMinDelay());
        assertNotNull(ConfigUtil.getDbPoolConfig().getRegisterJmxPool());
        assertNotNull(ConfigUtil.getDbPoolConfig().getUseResetConnection());
        assertNotNull(ConfigUtil.getDbPoolConfig().getStaticGlobal());
        assertNotNull(ConfigUtil.getDbPoolConfig().getMaxIdleTime());
        assertNotNull(ConfigUtil.getDbPoolConfig().getConnTimeOut());

        assertNotNull(ConfigUtil.getLoggerConfig());
        assertNotNull(ConfigUtil.getLoggerConfig().getPath());
        assertNotNull(ConfigUtil.getLoggerConfig().getEncoding());
        assertNotNull(ConfigUtil.getLoggerConfig().getLimit());
        assertNotNull(ConfigUtil.getLoggerConfig().getCount());
    }
}