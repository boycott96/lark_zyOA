package ink.lch.config.component;

import ink.lch.config.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnowflakeComponent {

    @Value("${server.data_center_id}")
    private long dataCenterId;

    @Value("${server.work_id}")
    private long workId;

    private static volatile SnowflakeIdWorker instance;

    public SnowflakeIdWorker getInstance() {
        if (instance == null) {
            synchronized (SnowflakeIdWorker.class) {
                if (instance == null) {
                    log.info("Server Instance, workId = {}, dataCenterId = {}", workId, dataCenterId);
                    instance = new SnowflakeIdWorker(workId, dataCenterId);
                }
            }
        }
        return instance;
    }
}
