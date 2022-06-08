package util;

import org.junit.jupiter.api.Test;

public class UtilTest {
    @Test
    public void distanceCalcTest() {
        double d = Math.round((Util.calculateDistance(10.5,10.5,126.904236,7.447644)/1000)*10000)/10000.0;
        System.out.println(d);
        double r = 8890.5213;
        System.out.println((r/1000));
    }
}
