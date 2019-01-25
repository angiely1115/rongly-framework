/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 认证服务器注册的第三方应用配置项
 * 
 * @author zhailiang
 *
 */
@Getter
@Setter
public class OAuth2ClientProperties {
	
	/**
	 * 第三方应用appId
	 */
	private String clientId;
	/**
	 * 第三方应用appSecret
	 */
	private String clientSecret;
	/**
	 * 针对此应用发出的token的有效时间
	 */
	private int accessTokenValidateSeconds = 7200;
	/**
	 * 刷新令牌的有效期
	 */
	private int refreshTokenValiditySeconds = 2592000;

}
