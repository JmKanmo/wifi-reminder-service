package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocationDate {
    private final Double posX;

    private final Double posY;

    private final LocalDateTime dateTime;
}
