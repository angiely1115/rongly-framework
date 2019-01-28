package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import com.xs.rongly.framework.stater.security.spring.security.core.SecurityCoreConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeController;
import com.xs.rongly.framework.stater.security.spring.security.core.social.SocialConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.qq.config.QQAutoConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.weixin.config.WeixinAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lvrongzhuan
 * @Description: security APP应用模式登陆授权
 * @Date: 2019/1/24 16:30
 * @Version: 1.0
 * modified by:
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(value = {SecurityCoreConfig.class, QQAutoConfig.class, WeixinAutoConfiguration.class, SocialConfig.class})
@Import(ScannerRegistrar.class)
public @interface EnableAPPSecurityConfig {

    /**
     * 扫描路径
     * @return
     */
    String[] basePackages() default {"com.xs.rongly.framework.stater.security.spring.security.app"};

    /**
     * 扫描的类
     * @return
     */
    Class<?>[] classs() default {ValidateCodeController.class};
}
