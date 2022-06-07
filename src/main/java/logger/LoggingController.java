package logger;

import util.ConfigUtil;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingController {
    private static Logger logger;
    private static FileHandler fileHandler;

    private LoggingController() {
    }

    static {
        try {
            fileHandler = new FileHandler(ConfigUtil.getLoggerConfig().getPath(), ConfigUtil.getLoggerConfig().getLimit(),
                    ConfigUtil.getLoggerConfig().getCount(), true);
            logger = Logger.getLogger(LoggingController.class.getClass().getName());
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setEncoding(ConfigUtil.getLoggerConfig().getEncoding());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, "[wifi-alarm] level-" + level + " , " + message);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }
}
