package com.xs.rongly.framework.stater.web.error;

import com.vip.vjtools.vjkit.collection.MapUtil;
import com.xs.rongly.framework.stater.web.exception.BizException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 */
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    public GlobalErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = MapUtil.newHashMapWithCapacity(4,0.75f);
        errorAttributes.put("timestamp", ZonedDateTime.now());
        Throwable error = this.getError(request);
        this.addStatus(errorAttributes, request);
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


    private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Integer status = (Integer)(requestAttributes.getAttribute("javax.servlet.error.status_code",0));
        if (status == null) {
            errorAttributes.put("status", 999);
        } else {
            errorAttributes.put("status", status);
        }
    }
}