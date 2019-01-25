/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.code.sms;

import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCode;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeGenerator;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 短信验证码生成器
 * 
 * @author zhailiang
 *
 */
//@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = ThreadLocalRandom.current().nextInt(100000,securityProperties.getCode().getSms().getLength())+"";
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	

}
