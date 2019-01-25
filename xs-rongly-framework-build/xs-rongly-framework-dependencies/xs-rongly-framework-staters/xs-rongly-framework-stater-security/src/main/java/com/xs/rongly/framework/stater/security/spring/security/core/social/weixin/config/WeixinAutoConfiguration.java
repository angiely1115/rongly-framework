/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.social.weixin.config;

import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import com.xs.rongly.framework.stater.security.spring.security.core.social.view.ImoocConnectView;
import com.xs.rongly.framework.stater.security.spring.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "rongly.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
	 * #createConnectionFactory()
	 */
	protected ConnectionFactory<?> createConnectionFactory() {
		SecurityProperties.SocialProperties.WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}
	
	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ImoocConnectView();
	}

	// 后补：做到处理注册逻辑的时候发现的一个bug：登录完成后，数据库没有数据，但是再次登录却不用注册了
	// 就怀疑是否是在内存中存储了。结果果然发现这里父类的内存ConnectionRepository覆盖了SocialConfig中配置的jdbcConnectionRepository
	// 这里需要返回null，否则会返回内存的 ConnectionRepository
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return null;
	}
}
