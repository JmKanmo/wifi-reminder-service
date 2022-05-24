package util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class ThreadUtil {
    private static final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(ConfigUtil.getWorkConfig().getWorkCount(), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    private ThreadUtil() {
    }

    public static ExecutorService getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
