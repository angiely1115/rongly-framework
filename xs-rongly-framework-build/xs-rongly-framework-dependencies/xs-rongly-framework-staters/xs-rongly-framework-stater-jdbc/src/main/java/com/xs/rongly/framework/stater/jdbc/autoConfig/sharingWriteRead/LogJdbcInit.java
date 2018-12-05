package com.xs.rongly.framework.stater.jdbc.autoConfig.sharingWriteRead;

import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/12/3 15:47
 * @Version: 1.0
 * modified by:
 */
public class LogJdbcInit implements BeanFactoryPostProcessor, EnvironmentAware {
    private ConfigurableEnvironment environment;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.initLog4Jdbc();
    }
    private void initLog4Jdbc() {
        for (final String property : PROPERTIES_TO_COPY) {
            if (this.environment.containsProperty(property)) {
                System.setProperty(property, this.environment.getProperty(property));
            }
        }
        System.setProperty("log4jdbc.spylogdelegator.name", this.environment
                .getProperty("log4jdbc.spylogdelegator.name", Slf4jSpyLogDelegator.class.getName()));
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    /**
     * 为了打印Sql语言配置
     */
    private static final String[] PROPERTIES_TO_COPY = {
            "log4jdbc.log4j2.properties.file",
            "log4jdbc.debug.stack.prefix",
            "log4jdbc.sqltiming.warn.threshold",
            "log4jdbc.sqltiming.error.threshold",
            "log4jdbc.dump.booleanastruefalse",
            "log4jdbc.dump.fulldebugstacktrace",
            "log4jdbc.dump.sql.maxlinelength",
            "log4jdbc.statement.warn",
            "log4jdbc.dump.sql.select",
            "log4jdbc.dump.sql.insert",
            "log4jdbc.dump.sql.update",
            "log4jdbc.dump.sql.delete",
            "log4jdbc.dump.sql.create",
            "log4jdbc.dump.sql.addsemicolon",
            "log4jdbc.auto.load.popular.drivers",
            "log4jdbc.drivers",
            "log4jdbc.trim.sql",
            "log4jdbc.trim.sql.extrablanklines",
            "log4jdbc.suppress.generated.keys.exception",
            "log4jdbc.log4j2.properties.file",
    };

}
