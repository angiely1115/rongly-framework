package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import com.xs.rongly.framework.stater.security.spring.security.core.SecurityCoreConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeController;
import com.xs.rongly.framework.stater.security.spring.security.core.social.SocialConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.qq.config.QQAutoConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.social.weixin.config.WeixinAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author: lvrongzhuan
 * @Description: security APP应用模式登陆授权
 * @Date: 2019/1/24 16:30
 * @Version: 1.0
 * modified by:
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(value = {SecurityCoreConfig.class, QQAutoConfig.class, WeixinAutoConfiguration.class, SocialConfig.class})
@SecurityScan
public @interface EnableAPPSecurityConfig {

    /**
     * 扫描路径
     * @return
     */
    @AliasFor(annotation = SecurityScan.class, attribute = "basePackages")
    String[] scanbasePackages() default {"com.xs.rongly.framework.stater.security.spring.security.app"};

    /**
     * 扫描的类
     * @return
     */
    @AliasFor(annotation = SecurityScan.class, attribute = "classs")
    Class<?>[] scanclasss() default {ValidateCodeController.class};
}
