/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.browser.authorize;

import com.xs.rongly.framework.stater.security.spring.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 浏览器环境默认的授权配置，对常见的静态资源，如js,css，图片等不验证身份
 * 可以通过核心配置哪些路径不需要授权配置
 *
 */
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(HttpMethod.GET,
			"/**/*.js",
			"/**/*.css",
			"/**/*.jpg",
			"/**/*.png",
			"/**/*.gif").permitAll();
		return false;
	}

}
