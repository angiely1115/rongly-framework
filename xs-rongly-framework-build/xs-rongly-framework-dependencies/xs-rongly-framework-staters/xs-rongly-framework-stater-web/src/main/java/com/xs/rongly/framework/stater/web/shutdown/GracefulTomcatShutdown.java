package com.xs.rongly.framework.stater.web.shutdown;

import com.xs.rongly.framework.stater.web.shutdown.GracefulShutdownProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


@Slf4j
public class GracefulTomcatShutdown
        implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private volatile Connector connector;
    @Getter
    private Date startShutdown;
    private Date stopShutdown;


    private final GracefulShutdownProperties shutdownProperties;


    public GracefulTomcatShutdown(GracefulShutdownProperties shutdownProperties) {
        this.shutdownProperties = shutdownProperties;
    }


    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }


    @Override
    public void onApplicationEvent(final ContextClosedEvent event) {
        final Executor executor = connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            log.info("executor is ThreadPoolExecutor");
            final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            if (threadPoolExecutor.isTerminated()) {
                log.info("thread pool executor is terminated");
            } else {
                try {
                    startShutdown = new Date();
                    log.info("We are now in down mode, please wait " + shutdownProperties
                            .getWait() + " second(s)...");
                    Thread.sleep(shutdownProperties.getWait() * 1000);
                    connector.pause();
                    log.info(
                            "Graceful shutdown in progress... We don't accept new connection... Wait after latest connections (max : " + shutdownProperties
                                    .getTimeout() + " seconds)... ");

                    threadPoolExecutor.shutdown();
                    if (!threadPoolExecutor
                            .awaitTermination(shutdownProperties.getTimeout(), TimeUnit.SECONDS)) {
                        log.warn("Tomcat thread pool did not shut down gracefully within " + shutdownProperties
                                .getTimeout() + " second(s). Proceeding with force shutdown");
                        threadPoolExecutor.shutdownNow();
                    } else {
                        log.debug("Tomcat thread pool is empty, we stop now");
                    }
                    stopShutdown = new Date();
                } catch (final InterruptedException ex) {
                    log.error("The await termination has been interrupted : " + ex.getMessage());
                    Thread.currentThread().interrupt();
                } finally {
                    if (stopShutdown != null && startShutdown != null) {
                        final long seconds = (stopShutdown.getTime() - startShutdown.getTime()) / 1000;
                        log.info("Shutdown performed in " + seconds + " second(s)");
                    }
                }
            }
        } else {
            log.info("executor is not ThreadPoolExecutor");
        }
    }
}
