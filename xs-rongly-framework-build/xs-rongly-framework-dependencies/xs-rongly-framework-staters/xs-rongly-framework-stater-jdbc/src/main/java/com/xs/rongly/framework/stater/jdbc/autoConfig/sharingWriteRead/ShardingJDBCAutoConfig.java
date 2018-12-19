package com.xs.rongly.framework.stater.jdbc.autoConfig.sharingWriteRead;

import com.vip.vjtools.vjkit.collection.MapUtil;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: lvrongzhuan
 * @Description: sharing jdbc读写分离配置
 * @Date: 2018/11/30 9:15
 * @Version: 1.0
 * modified by:
 */
@Configuration
@Import(LogJdbcInit.class)
@EnableConfigurationProperties({SpringBootMasterSlaveRuleConfigurationProperties.class,RonglyJdbCProperties.class,SpringBootShardingRuleConfigurationProperties.class})
public class ShardingJDBCAutoConfig {
    @Autowired
    private SpringBootMasterSlaveRuleConfigurationProperties masterSlaveProperties;
    @Autowired
    private SpringBootShardingRuleConfigurationProperties shardingProperties;
    @Autowired
    private RonglyJdbCProperties ronglyJdbCProperties;



    @Bean
    public DataSource dataSource() throws SQLException {
        Map<String,DataSource> dataSourceMap =  this.createMapDataSource();
        String master = masterSlaveProperties.getMasterDataSourceName();
        DataSource dataSource = null;
        if(master!=null){
            dataSource  =  MasterSlaveDataSourceFactory.createDataSource(
                    dataSourceMap, masterSlaveProperties.getMasterSlaveRuleConfiguration(), masterSlaveProperties.getConfigMap(), masterSlaveProperties.getProps());
        }else {
            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingProperties.getShardingRuleConfiguration(), shardingProperties.getConfigMap(), shardingProperties.getProps());
        }

        DataSourceSpy dataSourceSpy = new DataSourceSpy(dataSource);
        return dataSourceSpy;
    }

    public Map<String,DataSource> createMapDataSource(){
        Map<String, RonglyJdbCProperties.JdbCProperties> jdbcPropertiesMap =  ronglyJdbCProperties.getRonglyJdbc();
        Map<String,DataSource> dataSourceMap = MapUtil.newHashMapWithCapacity(jdbcPropertiesMap.size(),0.5f);
        jdbcPropertiesMap.forEach((key,value)->{
            HikariDataSource hikariDataSource = this.createHikariDataSource(value);
            dataSourceMap.put(key,hikariDataSource);
        });
        return dataSourceMap;
    }



    private HikariDataSource createHikariDataSource(RonglyJdbCProperties.JdbCProperties mysqlProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(mysqlProperties.getJdbcUrl());
        hikariDataSource.setUsername(mysqlProperties.getUserName());
        hikariDataSource.setPassword(mysqlProperties.getPassword());

        JdbcPoolConfig jdbcPoolConfig = mysqlProperties.getJdbcPoolConfig();
        hikariDataSource.setAutoCommit(jdbcPoolConfig.isAutoCommit());
        hikariDataSource.setConnectionTimeout(jdbcPoolConfig.getConnectionTimeout());
        hikariDataSource.setIdleTimeout(jdbcPoolConfig.getIdleTimeout());
        hikariDataSource.setMaxLifetime(jdbcPoolConfig.getMaxLifetime());
        hikariDataSource.setMaximumPoolSize(jdbcPoolConfig.getMaximumPoolSize());
        hikariDataSource.setMinimumIdle(jdbcPoolConfig.getMinimumIdle());
        hikariDataSource
                .setInitializationFailTimeout(jdbcPoolConfig.getInitializationFailTimeout());
        hikariDataSource.setIsolateInternalQueries(jdbcPoolConfig.isIsolateInternalQueries());
        hikariDataSource.setReadOnly(jdbcPoolConfig.isReadOnly());
        hikariDataSource.setRegisterMbeans(jdbcPoolConfig.isRegisterMbeans());
        Optional.ofNullable(jdbcPoolConfig.getDriverClassName())
                .ifPresent(hikariDataSource::setDriverClassName);
        hikariDataSource.setDriverClassName(mysqlProperties.getDriverClassName());
        hikariDataSource.setValidationTimeout(jdbcPoolConfig.getValidationTimeout());
        hikariDataSource.setLeakDetectionThreshold(jdbcPoolConfig.getLeakDetectionThreshold());
        hikariDataSource.setConnectionInitSql(jdbcPoolConfig.getConnectionInitSql());
        return hikariDataSource;
    }


}
