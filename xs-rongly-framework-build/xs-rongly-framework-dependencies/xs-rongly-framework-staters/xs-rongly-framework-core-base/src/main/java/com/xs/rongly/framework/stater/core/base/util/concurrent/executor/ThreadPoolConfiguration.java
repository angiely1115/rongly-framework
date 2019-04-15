package com.xs.rongly.framework.stater.core.base.util.concurrent.executor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Data
@NoArgsConstructor
public class ThreadPoolConfiguration implements AsyncConfigurer {
    /**
     * 设置默认值配置:${threadPool-corePoolSize:10}
     */
    @Value("${threadPool-corePoolSize:20}")
    @Nullable
    private Integer corePoolSize = 20;
    @Value("${threadPool-maxPoolSize:30}")
    @Nullable
    private Integer maxPoolSize = 40;
    @Value("${threadPool-queueCapacity:100}")
    @Nullable
    private Integer queueCapacity = 300;
  @Override
  @Bean
  @Primary
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setCorePoolSize(corePoolSize);
    threadPool.setMaxPoolSize(maxPoolSize);
    //设置等任务执行完成线程池再关闭
    threadPool.setWaitForTasksToCompleteOnShutdown(true);
    threadPool.setAwaitTerminationSeconds(60 * 15);
    threadPool.setThreadNamePrefix("rongly-Async-");
    threadPool.setQueueCapacity(queueCapacity);
    threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    threadPool.initialize();
    return threadPool;
  }
}