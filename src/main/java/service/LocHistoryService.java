package service;

import domain.LocationDate;
import repository.DaoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocHistoryService {
    private final DaoManager daoManager = new DaoManager();

    public List<LocationDate> getLocationHistories() {
        Optional<List<LocationDate>> locationHistories = daoManager.searchLocationHistoryInfos();

        if (locationHistories.isPresent()) {
            return locationHistories.get();
        } else {
            return new ArrayList<>();
        }
    }

    public int insertLocHistoryInfo(Double d1, Double d2) {
        return daoManager.insertLocationInfo(d1, d2);
    }

    public int deleteLocHistoryInfo(Integer id) {
        return daoManager.deleteLocationInfo(id);
    }
}
