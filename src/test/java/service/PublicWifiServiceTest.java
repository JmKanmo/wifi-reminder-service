package service;

import domain.WifiInfo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublicWifiServiceTest {
    private final PublicWifiService publicWifiService = new PublicWifiService();

    @Test
    void requestWifiApiService() {
        try {
            assertNotEquals(publicWifiService.requestWifiApiService(), 0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getWifiNearestInfosByDBTest() {
        try {
            List<WifiInfo> wifiInfoList = publicWifiService.searchWifiNearestInfosByDB(35.5, 125.5, 0, 20);
            assertNotNull(wifiInfoList);
            assertEquals(wifiInfoList.size() >= 0, true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getWIfiNearestInfosDirectlyTest() {
        try {
            List<WifiInfo> wifiInfoList = publicWifiService.searchWifiNearestInfosDirectly(35.5, 125.5, 0, 20);
            assertNotNull(wifiInfoList);
            assertEquals(wifiInfoList.size() >= 0, true);
        } catch (Exception e) {
            fail();
        }
    }
}