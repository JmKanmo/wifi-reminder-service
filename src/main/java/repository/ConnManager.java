package repository;

import org.mariadb.jdbc.MariaDbPoolDataSource;
import util.ConfigUtil;
import util.SqlUtil;

import java.sql.*;

public class ConnManager {
    private static MariaDbPoolDataSource mariaDbPoolDataSource = new MariaDbPoolDataSource(ConfigUtil.getDbConfig().getUrl());

    static {
        try {
            // DB 연결 & 테이블 생성
            mariaDbPoolDataSource.setUser(ConfigUtil.getDbConfig().getUser());
            mariaDbPoolDataSource.setPassword(ConfigUtil.getDbConfig().getPassword());
            mariaDbPoolDataSource.setPoolName(ConfigUtil.getDbPoolConfig().getPoolName());
            mariaDbPoolDataSource.setLoginTimeout(ConfigUtil.getDbPoolConfig().getConnTimeOut());

            try (Connection connection = mariaDbPoolDataSource.getConnection();
                 PreparedStatement wifiInfoPreparedStatement = connection.prepareStatement(SqlUtil.CREATE_WIFI_INFO_TABLE_SQL);
                 PreparedStatement locationHistoryInfoPreparedStatement = connection.prepareStatement(SqlUtil.CREATE_LOCATION_INFO_TABLE_SQL);) {
                wifiInfoPreparedStatement.executeUpdate();
                locationHistoryInfoPreparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConnManager() {
    }

    public static Connection getConnection() {
        try {
            return mariaDbPoolDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        mariaDbPoolDataSource.close();
    }
}
