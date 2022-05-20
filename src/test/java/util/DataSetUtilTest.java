package util;

import domain.WifiInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataSetUtilTest {

    @BeforeEach
    public void hello() {
        DataSetUtil.clear();
    }

    @Test
    public void addTest() {
        try {
            int coreCount = Runtime.getRuntime().availableProcessors();

            for (int i = 0; i < coreCount; i++) {
                new Thread(() -> {
                    DataSetUtil.addWifiInfo(WifiInfo.builder().build());
                }).start();
            }

            Thread.sleep(1000);
            assertEquals(DataSetUtil.getSize(), coreCount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addListTest() {
        try {
            int coreCount = Runtime.getRuntime().availableProcessors();

            for (int i = 0; i < coreCount; i++) {
                new Thread(() -> {
                    List<WifiInfo> wifiInfoList = new ArrayList<>();
                    wifiInfoList.add(WifiInfo.builder().build());
                    wifiInfoList.add(WifiInfo.builder().build());
                    wifiInfoList.add(WifiInfo.builder().build());
                    DataSetUtil.addWifiInfo(wifiInfoList);
                }).start();
            }

            Thread.sleep(1000);
            assertEquals(DataSetUtil.getSize(), coreCount * 3);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getListTest() {
        try {
            int coreCount = Runtime.getRuntime().availableProcessors();

            for (int i = 0; i < coreCount; i++) {
                new Thread(() -> {
                    DataSetUtil.addWifiInfo(WifiInfo.builder().build());
                }).start();
            }

            Thread.sleep(1000);
            assertEquals(DataSetUtil.clearAndGetTotalWifiInfoList().size(), coreCount);
            assertEquals(DataSetUtil.getSize(), 0);
        } catch (Exception e) {
            fail();
        }
    }
}