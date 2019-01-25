/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.social.qq.connet;

import com.xs.rongly.framework.stater.security.spring.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author zhailiang
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
