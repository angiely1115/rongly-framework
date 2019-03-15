package com.xs.rongly.framework.stater.web.error;

import com.vip.vjtools.vjkit.collection.MapUtil;
import com.xs.rongly.framework.stater.core.base.BaseResult;
import com.xs.rongly.framework.stater.web.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * 统一异常处理
 */
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    public GlobalErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = MapUtil.newHashMapWithCapacity(4,0.75f);
        errorAttributes.put("timestamp", ZonedDateTime.now());
        Throwable error = super.getError(request);
        this.myAddStatus(errorAttributes, request);
        errorAttributes.put("code", "-1");
        if(error != null) {
            if (error instanceof BizException) {
                errorAttributes.put("code", ((BizException) error).getCode());
                errorAttributes.put("message", ((BizException) error).getMsg());
            } else {
                errorAttributes.put("message", error.getMessage());
            }

        }else {
            errorAttributes.put("message", "系统异常，请稍后再试");
        }
        return errorAttributes;
    }


    private void myAddStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Integer status = (Integer)(requestAttributes.getAttribute("javax.servlet.error.status_code",0));
        if (status == null) {
            errorAttributes.put("status", 999);
        } else {
            errorAttributes.put("status", status);
        }
    }

    @ConditionalOnClass({HttpMediaTypeNotSupportedException.class})
    @Configuration
    @RestControllerAdvice
    static class HttpMediaTypeNotSupportedExceptionConfiguration {
        HttpMediaTypeNotSupportedExceptionConfiguration() {
        }

        @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
        public BaseResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
            log.warn("HttpMediaTypeNotSupportedException",e);
            return BaseResult.fail(BaseErrorEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getCode(),BaseErrorEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getMessage());
        }
    }

    @ConditionalOnClass({ConstraintViolationException.class})
    @Configuration
    @RestControllerAdvice
    static class ConstraintViolationExceptionConfiguration {
        ConstraintViolationExceptionConfiguration() {
        }

        @ExceptionHandler({ConstraintViolationException.class})
        public BaseResult handleConstraintViolationException(ConstraintViolationException e) {
            log.warn("ConstraintViolationException", e);
            return BaseResult.fail(BaseErrorEnum.PARAMETER_ERROR.getCode(), ConstraintViolationExceptionHelper.firstErrorMessage(e.getConstraintViolations()));
        }

    }
    static class ConstraintViolationExceptionHelper {
        private ConstraintViolationExceptionHelper() {
        }
        static String firstErrorMessage(Set<ConstraintViolation<?>> constraintViolations) {
            return Optional.ofNullable(constraintViolations).orElseGet(HashSet::new).stream()
                    .findFirst()
                    .map(ConstraintViolation::getMessage).orElse("");
        }
    }
    @ConditionalOnClass({MissingServletRequestParameterException.class})
    @Configuration
    @RestControllerAdvice
    static class MissingServletRequestParameterExceptionConfiguration {
        MissingServletRequestParameterExceptionConfiguration() {
        }
        @ExceptionHandler({MissingServletRequestParameterException.class})
        public BaseResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
            log.warn("MissingServletRequestParameterException", e);
            return BaseResult.fail(BaseErrorEnum.PARAMETER_ERROR.getCode(), String.format("参数%s未传", e.getParameterName()));
        }
    }

    @ConditionalOnClass({HttpRequestMethodNotSupportedException.class})
    @Configuration
    @RestControllerAdvice
    static class HttpRequestMethodNotSupportedExceptionConfiguration {
        HttpRequestMethodNotSupportedExceptionConfiguration() {
        }

        @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
        public BaseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
            log.warn("HttpRequestMethodNotSupportedException", e);
            return BaseResult.fail(BaseErrorEnum.HTTP_METHOD_NOT_ALLOW_ERROR.getCode(),BaseErrorEnum.HTTP_METHOD_NOT_ALLOW_ERROR.getMessage());
        }
    }

    @ConditionalOnClass({MethodArgumentNotValidException.class})
    @Configuration
    @RestControllerAdvice
    static class MethodArgumentNotValidExceptionConfiguration {
        MethodArgumentNotValidExceptionConfiguration() {
        }

        @ExceptionHandler({MethodArgumentNotValidException.class})
        public BaseResult handMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            log.warn("MethodArgumentNotValidException", e);
            return BaseResult.fail(BaseErrorEnum.PARAMETER_ERROR.getCode(), MethodArgumentNotValidExceptionHelper.firstErrorMessage(e.getBindingResult()));
        }
    }

    static class MethodArgumentNotValidExceptionHelper {
        private MethodArgumentNotValidExceptionHelper() {
        }
        static String firstErrorMessage(BindingResult bindingResult) {
            return bindingResult.getAllErrors().stream().findFirst().map(b->b.getDefaultMessage()).orElse("");
        }
    }
    @ConditionalOnClass(BindException.class)
    @Configuration
    @RestControllerAdvice
    static class BindExceptionConfiguration {
        @ExceptionHandler(BindException.class)
        public BaseResult handBindException(BindException e) {
            return BaseResult.fail(BaseErrorEnum.PARAMETER_ERROR.getCode(),
                    BindExceptionHelper.firstErrorMessage(e.getBindingResult()));
        }

        static class BindExceptionHelper {
            private BindExceptionHelper() {
            }
            static String firstErrorMessage(BindingResult bindingResult) {
                return bindingResult.getAllErrors().stream().findFirst()
                        .map(ObjectError::getDefaultMessage).orElse("");
            }
        }
    }
}