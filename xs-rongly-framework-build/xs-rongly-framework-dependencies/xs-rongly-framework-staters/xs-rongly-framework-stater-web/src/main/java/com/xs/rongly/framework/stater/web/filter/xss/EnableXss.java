package com.xs.rongly.framework.stater.web.filter.xss;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lvrongzhuan
 * @Description: 使用xss
 * @Date: 2019/1/18 14:24
 * @Version: 1.0
 * modified by:
 */
@Import(XssRequestFilter.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableXss {
}
