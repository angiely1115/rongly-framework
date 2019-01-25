package com.xs.rongly.framework.stater.security.autoConfig.annotation;

import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.collection.SetUtil;
import com.xs.rongly.framework.stater.core.base.scanner.RonglyScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/25 15:05
 * @Version: 1.0
 * modified by:
 */
public class BrowserScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableBrowserSecurityConfig.class.getName()));
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        String[] strings = annoAttrs.getStringArray("basePackages");
        Class<?>[]  classes = annoAttrs.getClassArray("classs");
        if ((strings!=null&&strings.length>0)||classes.length>0) {
            Set<String> basePackages = SetUtil.newHashSetWithCapacity(strings.length+classes.length);
            for (String pkg : strings) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
            for (Class<?> clazz : classes) {
                basePackages.add(ClassUtils.getPackageName(clazz));
            }
            scanner.scan(StringUtils.toStringArray(basePackages));
        }

    }
}
