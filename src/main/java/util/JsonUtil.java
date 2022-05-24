package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.LocationDate;
import domain.WifiInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonUtil {
    public static JsonObject parseStrToJsonObject(String jsonStr) {
        JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
        JsonObject apiJsonObject = jsonObject.getAsJsonObject(ConfigUtil.getApiConfig().getName());

        if (apiJsonObject == null) {
            return jsonObject;
        }
        return apiJsonObject;
    }

    public static Optional<List<WifiInfo>> parseJsonToWifiInfo(final JsonObject jsonObject) {
        try {
            if (jsonObject == null ||
                    jsonObject.get("RESULT").getAsJsonObject().get("CODE").getAsString().equals("INFO-000") != true) {
                return Optional.empty();
            }

            JsonArray jsonArray = jsonObject.getAsJsonArray("row");
            List<WifiInfo> wifiInfoList = new ArrayList<>();

            jsonArray.forEach(elem -> {
                JsonObject jsonObj = elem.getAsJsonObject();

                LocationDate locationDate = LocationDate.builder()
                        .posX(Double.parseDouble(parseJsonObjToStr(jsonObj, "LAT", Double.class)))
                        .posY(Double.parseDouble(parseJsonObjToStr(jsonObj, "LNT", Double.class)))
                        .dateTime(Util.formatTimeStr(parseJsonObjToStr(jsonObj, "WORK_DTTM")))
                        .build();

                WifiInfo wifiInfo = WifiInfo.builder()
                        .adminNumber(parseJsonObjToStr(jsonObj, "X_SWIFI_MGR_NO"))
                        .borough(parseJsonObjToStr(jsonObj, "X_SWIFI_WRDOFC"))
                        .wifiName(parseJsonObjToStr(jsonObj, "X_SWIFI_MAIN_NM"))
                        .loadName(parseJsonObjToStr(jsonObj, "X_SWIFI_ADRES1"))
                        .detailAddress(parseJsonObjToStr(jsonObj, "X_SWIFI_ADRES2"))
                        .installPosition(parseJsonObjToStr(jsonObj, "X_SWIFI_INSTL_FLOOR"))
                        .installType(parseJsonObjToStr(jsonObj, "X_SWIFI_INSTL_TY"))
                        .installAgency(parseJsonObjToStr(jsonObj, "X_SWIFI_INSTL_MBY"))
                        .serviceType(parseJsonObjToStr(jsonObj, "X_SWIFI_SVC_SE"))
                        .netType(parseJsonObjToStr(jsonObj, "X_SWIFI_CMCWR"))
                        .installYear(Integer.parseInt(parseJsonObjToStr(jsonObj, "X_SWIFI_CNSTC_YEAR", Integer.class)))
                        .inOutDoorType(parseJsonObjToStr(jsonObj, "X_SWIFI_INOUT_DOOR"))
                        .wifiConnEnv(parseJsonObjToStr(jsonObj, "X_SWIFI_REMARS3"))
                        .locationDate(locationDate)
                        .build();

                wifiInfoList.add(wifiInfo);
            });

            return Optional.of(wifiInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static String parseJsonObjToStr(JsonObject jsonObj, String parseUnit) {
        return jsonObj.get(parseUnit).getAsString().replace("\"", "");
    }

    public static String parseJsonObjToStr(JsonObject jsonObj, String parseUnit, Object obj) {
        String parseStr = jsonObj.get(parseUnit).getAsString().replace("\"", "");

        if ((obj instanceof Object && parseStr.equals("")) ||
                (obj instanceof Double && parseStr.equals(""))) {
            return "0";
        }
        return parseStr;
    }
}
