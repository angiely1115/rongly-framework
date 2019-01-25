/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.rongly.framework.stater.core.base.autoJsonConfig.ObjectMapperJson;
import com.xs.rongly.framework.stater.security.spring.security.core.support.SimpleResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了imooc.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 *
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public DefaultLogoutSuccessHandler(String signOutSuccessUrl) {
		this.signOutSuccessUrl = signOutSuccessUrl;
	}

	private String signOutSuccessUrl;
	@Autowired
	private ObjectMapperJson objectMapper ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.logout.
	 * LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("退出成功");
		if (StringUtils.isBlank(signOutSuccessUrl)) {
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(objectMapper.obj2string(new SimpleResponse("退出成功")));
		} else {
			response.sendRedirect(signOutSuccessUrl);
		}
	}

}
