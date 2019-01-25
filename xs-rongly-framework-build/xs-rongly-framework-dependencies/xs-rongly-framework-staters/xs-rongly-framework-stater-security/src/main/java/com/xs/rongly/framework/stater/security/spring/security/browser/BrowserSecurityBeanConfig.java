/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.browser;

import com.xs.rongly.framework.stater.security.spring.security.browser.authentication.DefaultBrowserAuthenctiationFailureHandler;
import com.xs.rongly.framework.stater.security.spring.security.browser.authentication.DefaultBrowserAuthenticationSuccessHandler;
import com.xs.rongly.framework.stater.security.spring.security.browser.authorize.BrowserAuthorizeConfigProvider;
import com.xs.rongly.framework.stater.security.spring.security.browser.impl.SessionValidateCodeRepository;
import com.xs.rongly.framework.stater.security.spring.security.browser.logout.DefaultLogoutSuccessHandler;
import com.xs.rongly.framework.stater.security.spring.security.browser.session.DefaultExpiredSessionStrategy;
import com.xs.rongly.framework.stater.security.spring.security.browser.session.DefaultInvalidSessionStrategy;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

/**
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 * 
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new DefaultInvalidSessionStrategy(securityProperties);
	}
	
	/**
	 * 并发登录导致前一个session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new DefaultExpiredSessionStrategy(securityProperties);
	}
	
	/**
	 * 退出时的处理策略配置
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new DefaultLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
	}

	@Bean
	@ConditionalOnMissingBean(SimpleUrlAuthenticationFailureHandler.class)
	public DefaultBrowserAuthenctiationFailureHandler defaultBrowserAuthenctiationFailureHandler(){
		return new DefaultBrowserAuthenctiationFailureHandler();
	}

	@Bean
	@ConditionalOnMissingBean(SavedRequestAwareAuthenticationSuccessHandler.class)
	public DefaultBrowserAuthenticationSuccessHandler defaultBrowserAuthenticationSuccessHandler(){
		return new DefaultBrowserAuthenticationSuccessHandler();
	}

	@Bean
	@Order(Integer.MIN_VALUE)
	public BrowserAuthorizeConfigProvider browserAuthorizeConfigProvider(){
		return new BrowserAuthorizeConfigProvider();
	}
	@Bean
	@ConditionalOnMissingClass(value = "SessionValidateCodeRepository")
	public SessionValidateCodeRepository sessionValidateCodeRepository(){
		return new SessionValidateCodeRepository();
	}

	@Bean
	public BrowserSecurityConfig browserSecurityConfig(){
		return new BrowserSecurityConfig();
	}
}
