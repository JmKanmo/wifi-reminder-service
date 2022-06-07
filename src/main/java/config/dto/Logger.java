package config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Logger {
    private String path;
    private String encoding;
    private Integer limit;
    private Integer count;
}
