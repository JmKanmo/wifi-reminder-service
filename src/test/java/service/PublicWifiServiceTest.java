package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicWifiServiceTest {
    @Test
    void requestWifiApiService() {
        PublicWifiService publicWifiService = new PublicWifiService();

        try {
            assertNotEquals(publicWifiService.requestWifiApiService(), 0);
        } catch (Exception e) {
            fail();
        }
    }
}