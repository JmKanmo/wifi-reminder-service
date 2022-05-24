package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    public static LocalDateTime formatTimeStr(String timeStr) {
        return LocalDateTime.parse(timeStr, formatter);
    }

    public static String formatTimeStr(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}
