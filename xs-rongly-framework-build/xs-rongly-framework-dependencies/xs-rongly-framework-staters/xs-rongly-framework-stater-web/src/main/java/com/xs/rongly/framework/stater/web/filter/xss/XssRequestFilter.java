package com.xs.rongly.framework.stater.web.filter.xss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "xssFilter", urlPatterns = {"/*"})
@Slf4j
@Configuration
public class XssRequestFilter extends OncePerRequestFilter {

    /**
     * 使用htmlUtil来防御xss
     */
    @Value("${xss.htmlutil.type}")
    private boolean user_xss_htmlutil_type = true;
    /**
     * 不需要防御xss的访问路径
     */
    @Value("${xss.exclude.paths}")
    private String excludePaths = "/css/*,/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/reg";
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String requestURI = request.getRequestURI();
            //涉及保存操作的进行xss过滤
            if (!ifNoNeedXss(requestURI)) {
                log.info("xss拦截路径:{}",requestURI);
                if (user_xss_htmlutil_type) {
                    request = new XssHtmlUtilHttpServletRequestWrapper((HttpServletRequest) request);
                } else {
                    request = new XssHttpServletRequestWrapper((HttpServletRequest) request);
                }
            }
        filterChain.doFilter(request, response);
    }

    private boolean ifNoNeedXss(String requestURI) {
        String[] noNeedLoginXss = excludePaths.trim().split(",");
        for (String url : noNeedLoginXss) {
            if (antPathMatcher.match(url, requestURI)) {
                return true;
            }
        }
        return false;
    }
}