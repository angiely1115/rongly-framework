package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/28 21:34
 * @Version: 1.0
 * modified by:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ScannerRegistrar.class)
public @interface SecurityScan {
    /**
     * 扫描路径
     * @return
     */
    String[] basePackages() default {};

    /**
     * 扫描的类
     * @return
     */
    Class<?>[] classs() default {};
}
