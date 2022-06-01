package repository;

import domain.LocationDate;
import domain.WifiInfo;
import org.junit.jupiter.api.Test;
import service.PublicWifiService;
import util.SqlUtil;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoManagerTest {
    private final DaoManager daoManager = new DaoManager();

    @Test
    public void createTableTest() {
        try {
            DatabaseMetaData databaseMetaData = ConnManager.getConnection().getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, "WIFI_INFO", null);
            assertEquals(resultSet.next(), true);

            resultSet.close();

            resultSet = databaseMetaData.getTables(null, null, "LOCATION_HISTORY", null);
            assertEquals(resultSet.next(), true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void insertWifiInfosTest() {
        // TODO
        int reqCnt = new PublicWifiService().requestWifiApiService();

        try (PreparedStatement preparedStatement = ConnManager.getConnection().prepareStatement(SqlUtil.SELECT_COUNT_WIFI_INFO_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int rowCnt = resultSet.getInt("COUNT");

            if (resultSet.next()) {
                System.out.println(reqCnt + ", " + rowCnt);
                assertEquals(rowCnt, reqCnt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void checkWifiInfoExistTest() {
        try {
            assertEquals(daoManager.checkWifiInfoExist(), true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void wifiInfoSelectPageTest() {
        try {
            Optional<List<WifiInfo>> wifiInfoList = daoManager.searchNearestWifiInfo(126.89987, 37.554407, 1, 20);
            assertEquals(wifiInfoList.isPresent(), true);
            assertEquals(wifiInfoList.get().size(), 20);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void deleteWifiInfoTest() {
        try {
            int deleteCnt = daoManager.deleteWifiInfo();
            System.out.println("deleted wifi info : " + deleteCnt);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertLocationHistoryInfoTest() {
        try {
            int ret = daoManager.insertLocationInfo(37.552128, 126.8580352);
            assertEquals(ret, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void deleteLocationHistoryInfoTest() {
        try {
            int ret = daoManager.deleteLocationInfo(1);
            assertEquals(ret, 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteLocationHistoryInfoTestById(int id) {
        try {
            int ret = daoManager.deleteLocationInfo(id);
            assertEquals(ret, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void searchLocationHistoryInfoTest() {
        try {
            Optional<List<LocationDate>> locationDateList = daoManager.searchLocationHistoryInfos();

            assertEquals(locationDateList.isPresent(), true);

            if (locationDateList.isPresent() && locationDateList.get().size() > 0) {
                List<LocationDate> l = locationDateList.get();

                for (LocationDate elem : l) {
                    deleteLocationHistoryInfoTestById(elem.getId());
                }
            }
            assertEquals(daoManager.searchLocationHistoryInfos().get().size(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void dropTableTest() {
        try (PreparedStatement preparedStatement = ConnManager.getConnection().prepareStatement(SqlUtil.DROP_WIFI_INFO_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try (PreparedStatement preparedStatement = ConnManager.getConnection().prepareStatement(SqlUtil.DROP_LOCATION_HISTORY_INFO_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}