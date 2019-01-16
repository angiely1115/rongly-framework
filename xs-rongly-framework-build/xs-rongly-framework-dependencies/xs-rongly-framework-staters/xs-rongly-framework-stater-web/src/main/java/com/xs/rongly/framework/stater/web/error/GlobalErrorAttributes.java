package com.xs.rongly.framework.stater.web.error;

import com.xs.rongly.framework.stater.web.exception.BizException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(org.springframework.web.reactive.function.server.ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new HashMap<>(4);
        errorAttributes.put("timestamp", ZonedDateTime.now());
        Throwable error = this.getError(request);
        this.addStatus(errorAttributes, request);
        errorAttributes.put("code", "-1");
        if(error != null) {
            if (error instanceof BizException) {
                errorAttributes.put("code", ((BizException) error).getCode());
            }
            errorAttributes.put("message", error.getMessage());
        }else {
            errorAttributes.put("message", "系统异常，请稍后再试");
        }
        return errorAttributes;
    }


    private void addStatus(Map<String, Object> errorAttributes,org.springframework.web.reactive.function.server.ServerRequest request) {
        Integer status = (Integer)(request.attribute("javax.servlet.error.status_code").orElse(null));
        if (status == null) {
            errorAttributes.put("status", 999);
        } else {
            errorAttributes.put("status", status);
        }
    }
}