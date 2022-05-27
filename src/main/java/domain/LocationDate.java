package domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LocationDate {
    private Integer id;

    private final Double posX;

    private final Double posY;

    private final LocalDateTime dateTime;

    public static class LocationDateEnum {
        public static final String ID = "id";
        public static final String POS_X = "posX";
        public static final String POS_Y = "posY";
        public static final String DATETIME = "dateTime";
    }
}
