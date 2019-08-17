package com.xs.rongly.framework.stater.core.base.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: lvrongzhuan
 * @Description: 参数校验工具类
 * @Date: 2019/7/4 13:43
 * @Version: 1.0
 * modified by:
 */
public class ValidationUtils {

    private ValidationUtils() {
    }

    private static Validator validator = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();


    public static <T> void validate(String desc,T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<T> tConstraintViolation = constraintViolations.iterator().next();
            throw new IllegalArgumentException(desc.concat(":").concat(tConstraintViolation.getMessage()));

        }
    }

}