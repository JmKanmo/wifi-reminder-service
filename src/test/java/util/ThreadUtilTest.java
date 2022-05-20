package util;

import domain.WifiInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ThreadUtilTest {
    @BeforeEach
    void beforeEach() {
        DataSetUtil.clear();
    }

    @Test
    void startWorkTimeOutTest() {
        assertEquals(ThreadUtil.startWork(() -> {
            DataSetUtil.addWifiInfo(WifiInfo.builder().build());
            Thread.sleep((ConfigUtil.getWorkConfig().getTimeUnit() * 1000) + 1000); //  시간 초과
            return true;
        }), Optional.empty());

        assertEquals(DataSetUtil.clearAndGetTotalWifiInfoList().size(), 1);
    }

    @Test
    void startWorkNormalTest() {
        assertEquals(ThreadUtil.startWork(() -> {
            DataSetUtil.addWifiInfo(WifiInfo.builder().build());
            DataSetUtil.addWifiInfo(WifiInfo.builder().build());
            DataSetUtil.addWifiInfo(WifiInfo.builder().build());
            return true;
        }), Optional.of(true));

        assertEquals(DataSetUtil.clearAndGetTotalWifiInfoList().size(), 3);
    }

    @Test
    void startWorkLoopTest() {
        for (int i = 0; i < ConfigUtil.getWorkConfig().getWorkCount(); i++) {
            ThreadUtil.startWork(() -> {
                DataSetUtil.addWifiInfo(WifiInfo.builder().build());
                DataSetUtil.addWifiInfo(WifiInfo.builder().build());
                DataSetUtil.addWifiInfo(WifiInfo.builder().build());
                return true;
            });
        }

        assertEquals(DataSetUtil.clearAndGetTotalWifiInfoList().size(), ConfigUtil.getWorkConfig().getWorkCount() * 3);
    }
}