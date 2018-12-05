package com.xs.rongly.framework.stater.core.base.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @Author: lvrongzhuan
 * @Description: 子定义condition
 * @Date: 2018/11/24 11:49
 * @Version: 1.0
 * modified by:
 */
public class MapPropertyCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
       Map<String,Object> objectMap =  metadata.getAnnotationAttributes(ConditionalOnMapProperty.class.getName());
       String prefix = (String) objectMap.get("prefix");
       String enable =  context.getEnvironment().getProperty(prefix.concat(".enabled"));
       return new ConditionOutcome("true".equals(enable), MessageFormat.format("prefix:{0} is {1} match",prefix,enable));
    }
}
