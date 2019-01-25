/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.authentication;

import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 * 
 */
public class FormAuthenticationConfig {

	@Autowired
	protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
			.successHandler(imoocAuthenticationSuccessHandler)
			.failureHandler(imoocAuthenticationFailureHandler);
	}
	
}
