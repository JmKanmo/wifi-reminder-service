package config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DbPool {
    private String poolName;
    private int maxPoolSize;
    private int minPoolSize;
    private int poolValidMinDelay;
    private int maxIdleTime;
    private int connTimeOut;
    private String staticGlobal;
    private String useResetConnection;
    private String registerJmxPool;
}
