package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.LocationDate;
import domain.WifiInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JsonParserUtil {
    public static JsonObject parseStrToJsonObject(String jsonStr) {
        JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
        JsonObject apiJsonObject = jsonObject.getAsJsonObject(ConfigUtil.getApiConfig().getName());

        if (apiJsonObject == null) {
            return jsonObject;
        }
        return apiJsonObject;
    }

    public static Optional<List<WifiInfo>> parseJsonToWifiInfo(final JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("row");
        List<WifiInfo> wifiInfoList = new LinkedList<>();

        jsonArray.forEach(elem -> {
            JsonObject jsonObj = elem.getAsJsonObject();
            WifiInfo wifiInfo = WifiInfo.builder()
                    .distance(0L).adminNumber(jsonObj.get("X_SWIFI_MGR_NO").getAsString())
                    .borough(jsonObj.get("X_SWIFI_WRDOFC").getAsString())
                    .wifiName(jsonObj.get("X_SWIFI_MAIN_NM").getAsString())
                    .loadName(jsonObj.get("X_SWIFI_ADRES1").getAsString())
                    .detailAddress(jsonObj.get("X_SWIFI_ADRES2").getAsString())
                    .installPosition(jsonObj.get("X_SWIFI_INSTL_FLOOR").getAsString())
                    .installType(jsonObj.get("X_SWIFI_INSTL_TY").getAsString())
                    .installAgency(jsonObj.get("X_SWIFI_INSTL_MBY").getAsString())
                    .serviceType(jsonObj.get("X_SWIFI_SVC_SE").getAsString())
                    .netType(jsonObj.get("X_SWIFI_CMCWR").getAsString())
                    .installYear(jsonObj.get("X_SWIFI_CNSTC_YEAR").getAsInt())
                    .inOutDoorType(jsonObj.get("X_SWIFI_INOUT_DOOR").getAsString())
                    .wifiConnEnv(jsonObj.get("X_SWIFI_REMARS3").getAsString())
                    .locationDate(LocationDate.builder()
                            .posX(jsonObj.get("LAT").getAsDouble())
                            .posY(jsonObj.get("LNT").getAsDouble())
                            .dateTime(TimeUtil.formatTimeStr(jsonObj.get("WORK_DTTM").getAsString()))
                            .build())
                    .build();
            wifiInfoList.add(wifiInfo);
        });

        return Optional.of(wifiInfoList);
    }
}
