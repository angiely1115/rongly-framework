package com.xs.rongly.framework.stater.jdbc.autoConfig.sharingWriteRead;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/11/30 9:30
 * @Version: 1.0
 * modified by:
 */
@ConfigurationProperties(prefix = "rongly.jdbc")
@Data
public class RonglyJdbCProperties {
    private Map<String,JdbCProperties> ronglyJdbc;
    @Data
   public static class JdbCProperties{
        @NotBlank
        private String jdbcUrl;
        @NotBlank
        private String userName;
        @NotBlank
        private String password;
        @NotBlank
        private String  driverClassName;
        private JdbcPoolConfig jdbcPoolConfig;
    }
}
