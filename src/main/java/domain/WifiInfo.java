package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WifiInfo {
    private Double distance; // 거리

    private final String adminNumber; // 관리 번호

    private final String borough; // 자치구

    private final String wifiName; // wifi 네임

    private final String loadName; // 도로명 주소

    private final String detailAddress; // 상세 주소

    private final String installPosition; // 설치 위치

    private final String installType; // 설치 유형

    private final String installAgency; // 설치 기관

    private final String serviceType; // 서비스구분

    private final String netType; // 망종류

    private final Integer installYear; // 설치년도

    private final String inOutDoorType; // 실내외구분

    private final String wifiConnEnv; // wifi 접속 환경

    private final LocationDate locationDate; // 위치 날짜 정보

    public static class WifiEnum {
        public static final String DISTANCE = "distance";
        public static final String ADMIN_NUMBER = "adminNumber";
        public static final String BOROUGH = "borough";
        public static final String WIFI_NAME = "wifiName";
        public static final String LOAD_NAME = "loadName";
        public static final String DETAIL_ADDRESS = "detailAddress";
        public static final String INSTALL_POSITION = "installPosition";
        public static final String INSTALL_TYPE = "installType";
        public static final String INSTALL_AGENCY = "installAgency";
        public static final String SERVICE_TYPE = "serviceType";
        public static final String NET_TYPE = "netType";
        public static final String INSTALL_YEAR = "installYear";
        public static final String INOUT_DOOR_TYPE = "inOutDoorType";
        public static final String WIFI_CONN_ENV = "wifiConnEnv";
        public static final String POS_X = "posX";
        public static final String POS_Y = "posY";
        public static final String DATE_TIME = "dateTime";
    }
}
