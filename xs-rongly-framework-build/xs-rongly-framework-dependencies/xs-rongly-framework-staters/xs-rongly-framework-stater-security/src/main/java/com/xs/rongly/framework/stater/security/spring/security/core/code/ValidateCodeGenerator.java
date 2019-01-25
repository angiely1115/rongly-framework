/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 * @author zhailiang
 *
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成校验码
	 * @param request
	 * @return
	 */
	ValidateCode generate(ServletWebRequest request);
	
}
