package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import com.xs.rongly.framework.stater.security.spring.security.browser.BrowserSecurityBeanConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.SecurityCoreConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeController;
import com.xs.rongly.framework.stater.security.spring.security.core.social.SocialConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.qq.config.QQAutoConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.weixin.config.WeixinAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author: lvrongzhuan
 * @Description: security 浏览器模式登陆授权
 * @Date: 2019/1/24 16:30
 * @Version: 1.0
 * modified by:
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ImportAutoConfiguration(value = {BrowserSecurityBeanConfig.class,SecurityCoreConfig.class, QQAutoConfig.class, WeixinAutoConfiguration.class, SocialConfig.class})
@SecurityScan
public @interface EnableBrowserSecurityConfig {

    /**
     * 扫描路径
     * @return
     */
    @AliasFor(annotation = SecurityScan.class,attribute = "basePackages")
    String[] basePackages() default {"com.xs.rongly.framework.stater.security.spring.security.browser.controller"};

    /**
     * 扫描的类
     * @return
     */
    @AliasFor(annotation = SecurityScan.class,attribute = "classs")
    Class<?>[] classs() default {ValidateCodeController.class};
}
