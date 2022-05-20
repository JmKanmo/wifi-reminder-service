package util;

import java.util.Optional;
import java.util.concurrent.*;

public class ThreadUtil {
    private static final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static <T> Optional<T> startWork(Callable<T> callable) {
        try {
            Future<T> respFuture = threadPoolExecutor.submit(callable);
            T respStr = respFuture.get(ConfigUtil.getWorkConfig().getTimeUnit(), TimeUnit.SECONDS);
            return Optional.ofNullable(respStr);
        } catch (Exception e) {
            // TODO 로그 출력
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
