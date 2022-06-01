package util;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    public static final Gson gson = new Gson();

    public static String formatWifiDateTimeStr(String timeStr) {
        return LocalDateTime.parse(timeStr, formatter).format(formatter);
    }

    public static String formatLocationHistoryDateTimeStr(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }
}
