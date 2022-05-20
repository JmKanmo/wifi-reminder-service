package config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Api {
    private String url;
    private String name;
    private String key;
    private String type;
    private String encoding;
}
