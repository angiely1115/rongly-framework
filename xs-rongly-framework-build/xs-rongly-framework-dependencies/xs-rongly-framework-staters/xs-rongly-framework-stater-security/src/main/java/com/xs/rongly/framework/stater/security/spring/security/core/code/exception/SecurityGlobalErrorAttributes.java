package com.xs.rongly.framework.stater.security.spring.security.core.code.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/28 13:05
 * @Version: 1.0
 * modified by:
 */

public class SecurityGlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
/*        UnauthorizedException
                InvalidTokenException*/
        return super.getErrorAttributes(webRequest, includeStackTrace);
    }
}
