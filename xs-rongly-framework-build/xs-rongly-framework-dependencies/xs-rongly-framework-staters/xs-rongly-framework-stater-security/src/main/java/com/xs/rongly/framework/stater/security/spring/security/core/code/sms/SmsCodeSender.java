/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.code.sms;

/**
 * @author zhailiang
 *
 */
public interface SmsCodeSender {

	/**
	 * 验证码发送
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
