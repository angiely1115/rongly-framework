//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xs.rongly.framework.stater.web.shutdown;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
@ConditionalOnProperty(
    value = {"hn-shutdown.graceful.enabled"},
    matchIfMissing = true
)
@EnableConfigurationProperties({GracefulShutdownProperties.class})
@ConditionalOnBean({TomcatServletWebServerFactory.class})
@ConditionalOnClass({Servlet.class, Tomcat.class})
public class GracefulShutdownAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GracefulShutdownAutoConfiguration.class);
    private GracefulShutdownProperties gracefulShutdownProperties;

    public GracefulShutdownAutoConfiguration(GracefulShutdownProperties gracefulShutdownProperties) {
        this.gracefulShutdownProperties = gracefulShutdownProperties;
    }

    @Bean
    public GracefulTomcatShutdown gracefulTomcatShutdown() {
        log.info("register tomcat graceful shutdown ... ");
        return new GracefulTomcatShutdown(this.gracefulShutdownProperties);
    }

    @Bean
    public WebServerFactoryCustomizer tomcatFactoryCustomizer() {
        return server -> {
            if (server instanceof TomcatServletWebServerFactory) {
                ((TomcatServletWebServerFactory) server).addConnectorCustomizers(gracefulTomcatShutdown());
            }
        };
    }
}
