package com.xs.rongly.framework.stater.jdbc.autoConfig.sharingWriteRead;

import io.shardingsphere.core.yaml.masterslave.YamlMasterSlaveRuleConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rongly.sharding.jdbc.config.masterslave")
public class SpringBootMasterSlaveRuleConfigurationProperties extends YamlMasterSlaveRuleConfiguration {
}