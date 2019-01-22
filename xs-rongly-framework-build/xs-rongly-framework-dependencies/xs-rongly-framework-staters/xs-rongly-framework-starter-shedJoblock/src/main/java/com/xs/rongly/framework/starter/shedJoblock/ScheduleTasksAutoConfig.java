package com.xs.rongly.framework.starter.shedJoblock;

import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolBuilder;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SchedulerLock;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Author: lvrongzhuan
 * @Description: 定时任务配置
 * @Date: 2019/1/22 14:08
 * @Version: 1.0
 * modified by:
 */
@Slf4j
@Configuration
//@ConditionalOnBean(RedisConnectionFactory.class)
@EnableScheduling
//最大锁10分钟
@EnableSchedulerLock(defaultLockAtMostFor = "PT10M")
public class ScheduleTasksAutoConfig implements SchedulingConfigurer {

    /**
     * 读取环境配置 避免锁冲突
     */
    @Value("${spring.profiles.active}")
    private String profile;
    /**
     * 执行任务的线程池
     */
    @Value("${rongly.scheduler.thread.pools:10}")
    private int scheddulesThreadPools;


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
    }

    /**
     * 任务锁提供者
     * @param connectionFactory
     * @return
     */
    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        return new RedisLockProvider(connectionFactory, profile);
    }
    /**
     * 计划任务线程池
     * @return
     */
    @Bean(name = "TaskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(scheddulesThreadPools);
        scheduler.setThreadNamePrefix("rongly-task-schedule-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(t -> log.error(
                "Unknown error occurred while executing task.", t
        ));
        scheduler.setRejectedExecutionHandler(
                (r, e) -> log.error(
                        "Execution of task {} was rejected for unknown reasons.", r
                )
        );
//        scheduler.initialize();
        return scheduler;
    }
}
