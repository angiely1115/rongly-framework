package com.xs.rongly.framework.starter.oss.autoConfig;

import com.xs.rongly.framework.starter.oss.AliYunOss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/4/4 15:20
 * @Version: 1.0
 * modified by:
 */
@Configuration
@EnableConfigurationProperties(AliYunOssPerproties.class)
public class AliYunOssAutoConfig {
    @Autowired
    private AliYunOssPerproties aliYunOssPerproties;

    @Bean
    public AliYunOss aliYunOss() {
        return new AliYunOss(aliYunOssPerproties.getBucket(),aliYunOssPerproties.getEndpoint(),aliYunOssPerproties.getAccessKeyId(),aliYunOssPerproties.getAccessKeySecret(),aliYunOssPerproties.getOssExpiretime());
    }
}
