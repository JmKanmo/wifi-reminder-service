package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WifiInfo {
    private final Long distance; // 거리

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
}
