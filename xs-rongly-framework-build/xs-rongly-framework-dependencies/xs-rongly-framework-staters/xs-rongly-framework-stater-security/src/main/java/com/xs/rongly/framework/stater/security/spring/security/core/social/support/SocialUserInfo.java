/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.social.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhailiang
 *
 */
@Getter
@Setter
@ToString
public class SocialUserInfo {
	
	private String providerId;
	
	private String providerUserId;
	
	private String nickname;
	
	private String headimg;

}
