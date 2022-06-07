package logger;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class LoggingControllerTest {
    @Test
    public void initTest() {
        try {
            assertNotNull(LoggingController.getLogger());
            assertNotNull(LoggingController.getFileHandler());
            LoggingController.log(Level.INFO, "hello world");
        } catch (Exception e) {
            fail();
        }
    }
}