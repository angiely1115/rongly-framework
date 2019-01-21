package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import com.xs.rongly.framework.stater.security.shiro.ShiroConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/21 11:56
 * @Version: 1.0
 * modified by:
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ShiroConfig.class)
public @interface EnableShiroConfig {
}
