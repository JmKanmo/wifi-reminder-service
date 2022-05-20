package http;

import org.junit.jupiter.api.Test;
import util.HttpUtil;

import static org.junit.jupiter.api.Assertions.*;

class HttpSenderTest {
    @Test
    public void sendHttpRequestTest() {
        try{
            String resp = HttpUtil.sendHttpRequest(1,1005);
            assertNotNull(resp);
//            System.out.println(resp);
        }catch (Exception e) {
            fail();
        }
    }
}