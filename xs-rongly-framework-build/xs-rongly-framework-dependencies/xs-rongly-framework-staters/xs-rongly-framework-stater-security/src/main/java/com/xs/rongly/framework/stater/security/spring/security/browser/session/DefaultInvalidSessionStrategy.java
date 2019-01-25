/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.browser.session;

import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的session失效处理策略
 *
 */
public class DefaultInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public DefaultInvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
