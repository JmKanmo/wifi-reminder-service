package repository;

import domain.LocationDate;
import domain.WifiInfo;
import util.ConfigUtil;
import util.SqlUtil;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteDao {
    private static Connection connection;

    static {
        try {
            // DB 연결 & 테이블 생성
            Class.forName(ConfigUtil.getDbConfig().getName());
            connection = DriverManager.getConnection(ConfigUtil.getDbConfig().getPath());
            Statement statement = connection.createStatement();
            statement.executeUpdate(SqlUtil.CREATE_TABLE_SQL);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertWifiInfos(List<WifiInfo> wifiInfoList) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlUtil.INSERT_WIFI_INFO_SQL)) {
            for (WifiInfo wifiInfo : wifiInfoList) {
                int idx = 0;
                preparedStatement.setString(++idx, wifiInfo.getAdminNumber());
                preparedStatement.setString(++idx, wifiInfo.getBorough());
                preparedStatement.setString(++idx, wifiInfo.getWifiName());
                preparedStatement.setString(++idx, wifiInfo.getLoadName());
                preparedStatement.setString(++idx, wifiInfo.getDetailAddress());
                preparedStatement.setString(++idx, wifiInfo.getInstallPosition());
                preparedStatement.setString(++idx, wifiInfo.getInstallType());
                preparedStatement.setString(++idx, wifiInfo.getInstallAgency());
                preparedStatement.setString(++idx, wifiInfo.getServiceType());
                preparedStatement.setString(++idx, wifiInfo.getNetType());
                preparedStatement.setInt(++idx, wifiInfo.getInstallYear());
                preparedStatement.setString(++idx, wifiInfo.getInOutDoorType());
                preparedStatement.setString(++idx, wifiInfo.getWifiConnEnv());
                preparedStatement.setDouble(++idx, wifiInfo.getLocationDate().getPosX());
                preparedStatement.setDouble(++idx, wifiInfo.getLocationDate().getPosY());
                preparedStatement.setString(++idx, Util.formatTimeStr(wifiInfo.getLocationDate().getDateTime()));

                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return wifiInfoList.size();
    }

    public int deleteWifiInfo() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlUtil.DELETE_WIFI_INFO_SQL)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public boolean checkWifiInfoExist() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlUtil.SELECT_COUNT_WIFI_INFO_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int cnt = resultSet.getInt("COUNT");
                return cnt > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<List<WifiInfo>> searchNearestWifiInfo(double posX, double posY, int offset, int cnt) {
        try (PreparedStatement preparedStatement = SqliteDao.getConnection().prepareStatement(SqlUtil.SELECT_PAGE_WIFI_INFO_SQL)) {
            int idx = 0;

            preparedStatement.setDouble(++idx, posY);
            preparedStatement.setDouble(++idx, posX);
            preparedStatement.setDouble(++idx, posY);
            preparedStatement.setDouble(++idx, posX);
            preparedStatement.setInt(++idx, offset);
            preparedStatement.setInt(++idx, cnt);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<WifiInfo> wifiInfoList = new ArrayList<>();

            while (resultSet.next()) {
                WifiInfo wifiInfo = WifiInfo.builder()
                        .distance(resultSet.getDouble("distance"))
                        .adminNumber(resultSet.getString("adminNumber"))
                        .borough(resultSet.getString("borough"))
                        .wifiName(resultSet.getString("wifiName"))
                        .loadName(resultSet.getString("loadName"))
                        .detailAddress(resultSet.getString("detailAddress"))
                        .installPosition(resultSet.getString("installPosition"))
                        .installType(resultSet.getString("installType"))
                        .installAgency(resultSet.getString("installAgency"))
                        .serviceType(resultSet.getString("serviceType"))
                        .netType(resultSet.getString("netType"))
                        .installYear(resultSet.getInt("installYear"))
                        .inOutDoorType(resultSet.getString("inOutDoorType"))
                        .wifiConnEnv(resultSet.getString("wifiConnEnv"))
                        .locationDate(LocationDate.builder()
                                .posX(resultSet.getDouble("posX"))
                                .posY(resultSet.getDouble("posY"))
                                .dateTime(Util.formatTimeStr(resultSet.getString("dateTime")))
                                .build())
                        .build();
                wifiInfoList.add(wifiInfo);
            }
            return Optional.ofNullable(wifiInfoList);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection.isClosed() != true) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
