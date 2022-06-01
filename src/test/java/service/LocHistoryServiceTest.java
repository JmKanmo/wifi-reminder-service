package service;

import domain.LocationDate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocHistoryServiceTest {
    private final LocHistoryService locHistoryService = new LocHistoryService();

    @Test
    void getLocationHistories() {
        List<LocationDate> locationDateList = locHistoryService.getLocationHistories();
        assertNotNull(locationDateList);
        assertEquals(locationDateList.size() >= 0, true);
    }

    @Test
    void insertLocHistoryInfo() {
        List<LocationDate> locationDateList = locHistoryService.getLocationHistories();
        locHistoryService.insertLocHistoryInfo(35.5, 125.5);
        assertEquals(locHistoryService.getLocationHistories().size(), locationDateList.size() + 1);
    }

    @Test
    void deleteLocHistoryInfo() {
        List<LocationDate> locationDateList = locHistoryService.getLocationHistories();
        int beforeLocHistorySize = locationDateList.size();
        int ret = locHistoryService.deleteLocHistoryInfo(locationDateList.get(0).getId());
        int afterLocHistorySize = locHistoryService.getLocationHistories().size();
        assertEquals(ret > 0, true);
        assertEquals(afterLocHistorySize == beforeLocHistorySize - 1, true);
    }
}