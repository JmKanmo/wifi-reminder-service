package config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Work {
    private int workCount;
    private int timeUnit;
}
