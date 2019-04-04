package com.xs.rongly.framework.starter.oss.autoConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: lvrongzhuan
 * @Description: aliyun oss 配置
 * @Date: 2019/4/4 15:19
 * @Version: 1.0
 * modified by:
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliYunOssPerproties {
    private String bucket;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    /**
     *下载链接有效期 单位分钟
     */
    private Long ossExpiretime;
}
