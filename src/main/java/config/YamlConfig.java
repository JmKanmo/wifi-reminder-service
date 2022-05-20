package config;

import config.dto.Api;
import config.dto.Work;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class YamlConfig {
    private Api api;
    private Work work;
}
