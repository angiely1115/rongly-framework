/**
 *
 */
package com.xs.rongly.framework.stater.security.spring.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhailiang
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "rongly.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();
    /**
     * 社交登录配置
     */
    private SocialProperties social = new SocialProperties();
    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();
    /**
     * 排除不需要验证的路径
     */
    private String[] excludePaths;

    @Getter
    @Setter
    public static class BrowserProperties {

        /**
         * session管理配置项
         */
        private SessionProperties session = new SessionProperties();
        /**
         * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
         */
        private String signInPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
        /**
         * '记住我'功能的有效时间，默认1小时
         */
        private int rememberMeSeconds = 3600;
        /**
         * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
         */
        private String signOutUrl;
        /**
         * 社交登录，如果需要用户注册，跳转的页面
         */
        private String signUpUrl = "/rongly-signUp.html";
        /**
         * 登录响应的方式，默认是json
         */
        private LoginResponseType signInResponseType = LoginResponseType.JSON;
        /**
         * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
         * <p>
         * 只在signInResponseType为REDIRECT时生效
         */
        private String singInSuccessUrl;

        /**
         * 登陆失败跳转页 只在signInResponseType为REDIRECT时生效
         */
        private String defaultFailureUrl;

        @Getter
        @Setter
        public static class SessionProperties {

            /**
             * 同一个用户在系统中的最大session数，默认1
             */
            private int maximumSessions = 1;
            /**
             * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
             * 为true的话表示存在一个登陆的session 后面登陆不了
             */
            private boolean maxSessionsPreventsLogin;
            /**
             * session失效时跳转的地址
             */
            private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

        }

    }

    /**
     * 验证码配置
     *
     * @author zhailiang
     */
    @Setter
    @Getter
    public static class ValidateCodeProperties {

        /**
         * 图片验证码配置
         */
        private ImageCodeProperties image = new ImageCodeProperties();
        /**
         * 短信验证码配置
         */
        private SmsCodeProperties sms = new SmsCodeProperties();
        @Getter
        @Setter
        public static class ImageCodeProperties extends SmsCodeProperties {

            public ImageCodeProperties() {
                setLength(4);
            }

            /**
             * 图片宽
             */
            private int width = 67;
            /**
             * 图片高
             */
            private int height = 23;

        }

        /**
         * @author zhailiang
         *
         */
        @Getter
        @Setter
        public static class SmsCodeProperties {

            /**
             * 验证码长度
             */
            private int length = 999999;
            /**
             * 过期时间
             */
            private int expireIn = 60;
            /**
             * 要拦截的url，多个url用逗号隔开，ant pattern
             */
            private String url;

        }
    }

    /**
     * 社交登录配置项
     *
     * @author zhailiang
     */
    @Setter
    @Getter
    @ToString
    public static class SocialProperties {

        /**
         * 社交登录功能拦截的url
         */
        private String filterProcessesUrl = "/auth";

        private QQProperties qq = new QQProperties();

        private WeixinProperties weixin = new WeixinProperties();

        @Setter
        @Getter
        @ToString
        public static class QQProperties {
            /**
             * Application id.
             */
            private String appId;

            /**
             * Application secret.
             */
            private String appSecret;
            /**
             * 第三方id，用来决定发起第三方登录的url，默认是 qq。
             */
            private String providerId = "qq";

        }
        /**
         * 微信登录配置项
         *
         * @author zhailiang
         *
         */
        @Getter
        @Setter
        public static class WeixinProperties {
            /**
             * Application id.
             */
            private String appId;

            /**
             * Application secret.
             */
            private String appSecret;
            /**
             * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
             */
            private String providerId = "weixin";


        }
    }

    /**
     * @author zhailiang
     */
    @Getter
    @Setter
    public class OAuth2Properties {

        /**
         * 使用jwt时为token签名的秘钥
         */
        private String jwtSigningKey = "imooc";

        /**
         * 客户端配置
         */
        private OAuth2ClientProperties[] clients = {};
    }
}

