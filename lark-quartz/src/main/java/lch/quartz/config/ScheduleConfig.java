package lch.quartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Value("${scheduled-thread-pool}")
    private Integer scheduledThreadPool;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }
    @Bean(destroyMethod = "shutdown")
    public Executor setTaskExecutors() {
        return Executors.newScheduledThreadPool(scheduledThreadPool); // scheduledThreadPool个线程来处理。
    }
}
