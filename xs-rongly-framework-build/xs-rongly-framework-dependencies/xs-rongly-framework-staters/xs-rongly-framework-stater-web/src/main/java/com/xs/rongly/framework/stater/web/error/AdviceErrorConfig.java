package com.xs.rongly.framework.stater.web.error;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @Author: lvrongzhuan
 * @Description: 统一异常处理
 * @Date: 2019/1/19 10:34
 * @Version: 1.0
 * modified by:
 */
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class AdviceErrorConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    public GlobalErrorAttributes globalErrorAttributes(){
        return new GlobalErrorAttributes();
    }
}
