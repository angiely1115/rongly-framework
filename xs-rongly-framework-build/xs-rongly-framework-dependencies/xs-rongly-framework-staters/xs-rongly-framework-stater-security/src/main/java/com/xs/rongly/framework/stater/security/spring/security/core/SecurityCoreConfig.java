/**
 * 
 */
package com.xs.rongly.framework.stater.security.spring.security.core;

import com.xs.rongly.framework.stater.security.spring.security.core.authentication.DefaultSocialUserDetailsService;
import com.xs.rongly.framework.stater.security.spring.security.core.authentication.DefaultUserDetailsService;
import com.xs.rongly.framework.stater.security.spring.security.core.authentication.FormAuthenticationConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.authorize.ImoocAuthorizeConfigManager;
import com.xs.rongly.framework.stater.security.spring.security.core.authorize.ImoocAuthorizeConfigProvider;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeFilter;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeGenerator;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeProcessorHolder;
import com.xs.rongly.framework.stater.security.spring.security.core.code.ValidateCodeSecurityConfig;
import com.xs.rongly.framework.stater.security.spring.security.core.code.image.ImageCodeGenerator;
import com.xs.rongly.framework.stater.security.spring.security.core.code.image.ImageCodeProcessor;
import com.xs.rongly.framework.stater.security.spring.security.core.code.sms.DefaultSmsCodeSender;
import com.xs.rongly.framework.stater.security.spring.security.core.code.sms.SmsCodeGenerator;
import com.xs.rongly.framework.stater.security.spring.security.core.code.sms.SmsCodeProcessor;
import com.xs.rongly.framework.stater.security.spring.security.core.code.sms.SmsCodeSender;
import com.xs.rongly.framework.stater.security.spring.security.core.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 默认密码处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


     @Bean
     @ConditionalOnMissingBean
     public UserDetailsService userDetailsService() {
     return new DefaultUserDetailsService();
     }

    /**
     * 默认认证器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SocialUserDetailsService socialUserDetailsService() {
        return new DefaultSocialUserDetailsService();
    }

    @Bean
    public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig(){
        return new SmsCodeAuthenticationSecurityConfig();
    }

    @Bean
    public FormAuthenticationConfig formAuthenticationConfig(){
        return new FormAuthenticationConfig();
    }

    @Bean
    public ImoocAuthorizeConfigManager imoocAuthorizeConfigManager(){
        return new ImoocAuthorizeConfigManager();
    }

    @Bean
    @Order(Integer.MIN_VALUE)
    public ImoocAuthorizeConfigProvider imoocAuthorizeConfigProvider(){
        return new ImoocAuthorizeConfigProvider();
    }

    @Bean("imageValidateCodeProcessor")
    @ConditionalOnMissingBean(name = "imageValidateCodeProcessor")
    public ImageCodeProcessor imageCodeProcessor(){
        return new ImageCodeProcessor();
    }

    @Bean(name = "smsValidateCodeGenerator")
    @ConditionalOnMissingBean
   public SmsCodeGenerator smsCodeGenerator(){
        return new SmsCodeGenerator();
   }
    /**
     * 图片验证码图片生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        return codeGenerator;
    }


    /**
     * 短信验证码发送器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    @Bean(name = "smsValidateCodeProcessor")
    public SmsCodeProcessor smsCodeProcessor(){
        return new SmsCodeProcessor();
    }

    @Bean
    public ValidateCodeFilter validateCodeFilter(){
        return new ValidateCodeFilter();
    }

    @Bean
    public ValidateCodeProcessorHolder validateCodeProcessorHolder(){
        return new ValidateCodeProcessorHolder();
    }

    @Bean
    public ValidateCodeSecurityConfig validateCodeSecurityConfig(){
        return new ValidateCodeSecurityConfig();
    }
}
