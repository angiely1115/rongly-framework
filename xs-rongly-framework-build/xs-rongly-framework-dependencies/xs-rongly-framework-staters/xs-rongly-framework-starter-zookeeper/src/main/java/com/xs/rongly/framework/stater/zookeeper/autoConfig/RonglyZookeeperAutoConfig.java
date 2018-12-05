package com.xs.rongly.framework.stater.zookeeper.autoConfig;

import com.xs.rongly.framework.stater.core.base.condition.ConditionalOnMapProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lvrongzhuan
 * @Description: zookeeper 自动配置类
 * @Date: 2018/11/22 9:57
 * @Version: 1.0
 * modified by:
 */
@Configuration
@ConditionalOnClass(CuratorFramework.class)
@EnableConfigurationProperties(value = RonglyZookeeperProperties.class)
@ConditionalOnMapProperty(prefix = "rongly.zookeeper",value = true)
@Slf4j
public class RonglyZookeeperAutoConfig {
    @Autowired
    private RonglyZookeeperProperties ronglyZookeeperProperties;
    @Autowired(required = false)
    private EnsembleProvider ensembleProvider;


    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy) throws Exception {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        if (this.ensembleProvider != null) {
            builder.ensembleProvider(this.ensembleProvider);
        } else {
            builder.connectString(ronglyZookeeperProperties.getConnectString());
        }
        builder.connectionTimeoutMs(ronglyZookeeperProperties.getConnectionTimeoutMs());
        CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
        curator.start();
        log.trace("blocking until connected to zookeeper for " + ronglyZookeeperProperties.getBlockUntilConnectedWait()
                + ronglyZookeeperProperties.getBlockUntilConnectedUnit());
        curator.blockUntilConnected(ronglyZookeeperProperties.getBlockUntilConnectedWait(),
                ronglyZookeeperProperties.getBlockUntilConnectedUnit());
        log.trace("connected to zookeeper");
        return curator;
    }

    @Bean
    public RetryPolicy exponentialBackoffRetry() {
        return new ExponentialBackoffRetry(ronglyZookeeperProperties.getBaseSleepTimeMs(),
                ronglyZookeeperProperties.getMaxRetries(),
                ronglyZookeeperProperties.getMaxSleepMs());
    }

    @Bean
    public ZookeeperOper zookeeperOper(CuratorFramework curatorFramework){
        return new ZookeeperOper(curatorFramework);
    }
}
