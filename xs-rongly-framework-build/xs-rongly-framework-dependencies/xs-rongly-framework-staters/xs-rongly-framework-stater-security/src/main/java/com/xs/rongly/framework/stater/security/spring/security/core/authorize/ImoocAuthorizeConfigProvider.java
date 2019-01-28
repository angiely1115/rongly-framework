/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.authorize;

import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityConstants;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 * 
 *
 */
public class ImoocAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
				SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
				SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
				securityProperties.getBrowser().getSignInPage(),
				securityProperties.getBrowser().getSignUpUrl(),
				securityProperties.getBrowser().getSession().getSessionInvalidUrl()
		).permitAll();
		if (ArrayUtils.isNotEmpty(securityProperties.getExcludePaths())) {
			config.antMatchers(securityProperties.getExcludePaths()).permitAll();
		}
		if (StringUtils.isNotBlank(securityProperties.getBrowser().getSignOutUrl())) {
			config.antMatchers(securityProperties.getBrowser().getSignOutUrl()).permitAll();
		}
//		config.antMatchers("/role").hasRole("ADMIN"); 配置访问/role路径需要ADMIN 角色
		//除了上面的请求其他请求都需要经过身份认证
		config.anyRequest().authenticated();

		return false;
	}

}
