package util;

public class SqlUtil {
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS WIFI_INFO ( " + "\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "\n"
                    + " adminNumber varchar(255) NOT NULL," + "\n"
                    + " borough varchar(255) NOT NULL," + "\n"
                    + " wifiName varchar(255) NOT NULL," + "\n"
                    + " loadName varchar(255) NOT NULL," + "\n"
                    + " detailAddress varchar(255) NOT NULL," + "\n"
                    + " installPosition varchar(255) NOT NULL," + "\n"
                    + " installType varchar(255) NOT NULL," + "\n"
                    + " installAgency varchar(255) NOT NULL," + "\n"
                    + " serviceType varchar(255) NOT NULL," + "\n"
                    + " netType varchar(255) NOT NULL," + "\n"
                    + " installYear INT NOT NULL," + "\n"
                    + " inOutDoorType varchar(255) NOT NULL," + "\n"
                    + " wifiConnEnv varchar(255) NOT NULL," + "\n"
                    + " posX DOUBLE NOT NULL," + "\n"
                    + " posY DOUBLE NOT NULL," + "\n"
                    + " dateTime TEXT NOT NULL);" + "\n\n\n"

                    + "CREATE TABLE IF NOT EXISTS LOCATION_HISTORY ( " + "\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "\n"
                    + " posX DOUBLE NOT NULL," + "\n"
                    + " posY DOUBLE NOT NULL," + "\n"
                    + " dateTime DATETIME NOT NULL);";

    public static final String DROP_TABLE_SQL = "DROP TABLE WIFI_INFO";
    public static final String INSERT_WIFI_INFO_SQL = "INSERT INTO WIFI_INFO(adminNumber, borough, wifiName, " +
            "loadName, detailAddress, installPosition, installType, installAgency, serviceType, netType, " +
            "installYear, inOutDoorType, wifiConnEnv, posX, posY, dateTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_LOCATION_HISTORY_SQL = "INSERT INTO LOCATION_HISTORY(posX,posY,dateTime) VALUES(?,?,?,?)";

    public static final String DELETE_WIFI_INFO_SQL = "DELETE FROM WIFI_INFO";
    public static final String SELECT_PAGE_WIFI_INFO_SQL = "select round(sqrt(power((posY - ?), 2) + power(posX - ?, 2)) * 100, 4) as distance,\n" +
            "       adminNumber,\n" +
            "       borough,\n" +
            "       wifiName,\n" +
            "       loadName,\n" +
            "       detailAddress,\n" +
            "       installPosition,\n" +
            "       installType,\n" +
            "       installAgency,\n" +
            "       serviceType,\n" +
            "       netType,\n" +
            "       installYear,\n" +
            "       inOutDoorType,\n" +
            "       wifiConnEnv,\n" +
            "       posX,\n" +
            "       posY,\n" +
            "       dateTime\n" +
            "from WIFI_INFO\n" +
            "order by abs(sqrt(power((abs(posY - ?)), 2) + power((abs(posX - ?)), 2)))\n" +
            "limit ?,?";

    public static final String SELECT_COUNT_WIFI_INFO_SQL = "SELECT COUNT(*) AS COUNT FROM WIFI_INFO";

    private SqlUtil() {
    }
}