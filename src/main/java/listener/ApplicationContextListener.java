package listener;

import logger.LoggingController;
import repository.ConnManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Level;

public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO
        LoggingController.log(Level.INFO,"ContextListener:contextDestroyed executed");
        ConnManager.closePool();
    }
}
