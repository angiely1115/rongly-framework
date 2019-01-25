/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.rongly.framework.stater.core.base.autoJsonConfig.ObjectMapperJson;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.LoginResponseType;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import com.xs.rongly.framework.stater.security.spring.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 浏览器环境下登录失败的默认处理器 真实业务可以覆盖
 * 
 */
public class DefaultBrowserAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapperJson objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		
		logger.error("登录失败",exception);
		String msg = exception.getMessage();
		if (exception instanceof BadCredentialsException) {
			msg = "用户名或密码错误";
		}
		if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSignInResponseType())) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(objectMapper.obj2string(new SimpleResponse(msg)));
		}else{
			super.setDefaultFailureUrl(securityProperties.getBrowser().getDefaultFailureUrl());
			super.onAuthenticationFailure(request, response, exception);
		}
		
	}
}
