package config;

import config.dto.Api;
import config.dto.Db;
import config.dto.DbPool;
import config.dto.Work;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class YamlConfig {
    private Api api;
    private Work work;
    private Db db;
    private DbPool dbPool;
}
