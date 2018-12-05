package com.xs.rongly.framework.stater.zookeeper.autoConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lvrongzhuan
 * @Description: zookeeper 属性配置类
 * @Date: 2018/11/22 10:14
 * @Version: 1.0
 * modified by:
 */
@ConfigurationProperties(prefix = "rongly.zookeeper")
@Data
public class RonglyZookeeperProperties {
    /**
     * Connection string to the Zookeeper cluster
     */
    @NotNull
    private String connectString = "localhost:2181";

    /**
     * Is Zookeeper enabled
     */
    private boolean enabled = true;

    /**
     * Initial amount of time to wait between retries
     */
    private Integer baseSleepTimeMs = 50;

    /**
     * Max number of times to retry
     */
    private Integer maxRetries = 10;

    /**
     * Max time in ms to sleep on each retry
     */
    private Integer maxSleepMs = 500;

    /**
     * Wait time to block on connection to Zookeeper
     */
    private Integer blockUntilConnectedWait = 10;

    private Integer connectionTimeoutMs = 30*1000;

    /**
     * The unit of time related to blocking on connection to Zookeeper
     */
    private TimeUnit blockUntilConnectedUnit = TimeUnit.SECONDS;
}
