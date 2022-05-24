package repository;

import domain.WifiInfo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.PublicWifiService;
import util.SqlUtil;
import util.Util;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDaoTest {
    private final SqliteDao sqliteDao = new SqliteDao();

    @Test
    @Order(1)
    public void createTableTest() {
        try {
            DatabaseMetaData databaseMetaData = SqliteDao.getConnection().getMetaData();
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
    @Order(6)
    public void dropTableTest() {
        try (PreparedStatement preparedStatement = SqliteDao.getConnection().prepareStatement(SqlUtil.DROP_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(2)
    public void insertWifiInfosTest() {
        // TODO
        int reqCnt = new PublicWifiService().requestWifiApiService();

        try (PreparedStatement preparedStatement = SqliteDao.getConnection().prepareStatement(SqlUtil.SELECT_COUNT_WIFI_INFO_SQL)) {
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
    @Order(5)
    public void deleteWifiInfoTest() {
        try {
            int deleteCnt = sqliteDao.deleteWifiInfo();
            System.out.println("deleted wifi info : " + deleteCnt);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(3)
    public void checkWifiInfoExistTest() {
        try {
            assertEquals(sqliteDao.checkWifiInfoExist(), true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Order(4)
    public void wifiInfoSelectPageTest() {
        try {
            Optional<List<WifiInfo>> wifiInfoList = sqliteDao.searchNearestWifiInfo(126.89987, 37.554407, 1, 20);
            assertEquals(wifiInfoList.isPresent(), true);
            assertEquals(wifiInfoList.get().size(), 20);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}