package com.xs.rongly.framework.stater.security.shiro;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/16 11:29
 * @Version: 1.0
 * modified by:
 */
@Component
@ConfigurationProperties(prefix = "rongly.shiro")
@Data
@NoArgsConstructor
public class ShiroProperties {
    /**
     * redis缓存过期时间 默认单位s
     */
    private long expireIn = 1800;

    /**
     * session超时时常 单位ms
     */
    private long sessionTimeout = 1800000;

    /**
     * ememberMe cookie有效时长，默认30天
     */
    private long cookieTimeout = 2592000;

    private LinkedList<String> filterChainDefinitionAnon;
    /**
     * 登陆url
     */
    private String loginUrl;

    /**
     * 登陆成功跳转url
     */
    private String successUrl;

    /**
     * 退出url
     */
    private String logoutUrl;

    /**
     * 未授权url
     */
    private String unauthorizedUrl = "/403";

    /**
     * sessionid的名称
     */
    private String sessionIdName = "rongly.session.id";


}
