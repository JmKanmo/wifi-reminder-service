package util;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class JsonParserUtilTest {
    @Test
    void parseStrToJsonObject() throws IOException {
        // 순서가 MAX 를 넘어가는 경우
        String jsonStr = HttpUtil.sendHttpRequest(15001, 16000);
        JsonObject jsonObject = JsonParserUtil.parseStrToJsonObject(jsonStr);
        assertEquals(jsonObject.get("RESULT").getAsJsonObject().get("CODE").getAsString(), "INFO-200");

        // 10건의 데이터 받아옴
        jsonStr = HttpUtil.sendHttpRequest(1, 10);
        jsonObject = JsonParserUtil.parseStrToJsonObject(jsonStr);

        assertEquals(jsonObject.get("RESULT").getAsJsonObject().get("CODE").getAsString(), "INFO-000");
        assertEquals(JsonParserUtil.parseJsonToWifiInfo(jsonObject).isPresent(), true);
        assertEquals(JsonParserUtil.parseJsonToWifiInfo(jsonObject).get().size(), 10);
    }

    @Test
    void parseJsonToWifiInfo() throws IOException {
        // 1000건의 데이터를 받아옴
        String jsonStr = HttpUtil.sendHttpRequest(13001, 14000);
        JsonObject jsonObject = JsonParserUtil.parseStrToJsonObject(jsonStr);

        assertEquals(jsonObject.get("RESULT").getAsJsonObject().get("CODE").getAsString(), "INFO-000");
        assertEquals(JsonParserUtil.parseJsonToWifiInfo(jsonObject).isPresent(), true);
        assertEquals(JsonParserUtil.parseJsonToWifiInfo(jsonObject).get().size(), 1000);
    }
}