package com.xs.rongly.framework.stater.web.error;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/19 10:34
 * @Version: 1.0
 * modified by:
 */
@Configuration
@ConditionalOnWebApplication
public class AdviceErrorConfig {

    @Bean
    public GlobalErrorAttributes globalErrorAttributes(){
        return new GlobalErrorAttributes();
    }
}
