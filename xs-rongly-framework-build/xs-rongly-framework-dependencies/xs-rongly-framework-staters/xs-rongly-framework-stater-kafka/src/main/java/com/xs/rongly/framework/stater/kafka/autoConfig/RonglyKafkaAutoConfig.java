package com.xs.rongly.framework.stater.kafka.autoConfig;

import com.vip.vjtools.vjkit.collection.MapUtil;
import com.xs.rongly.framework.stater.core.base.autoJsonConfig.JsonConfig;
import com.xs.rongly.framework.stater.core.base.autoJsonConfig.ObjectMapperJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.LogIfLevelEnabled;
import sun.awt.SunHints;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: lvrongzhuan
 * @Description: kafka自动配置类
 * @Date: 2018/11/22 10:59
 * @Version: 1.0
 * modified by:
 */
@Configuration
@AutoConfigureAfter(value = {JsonConfig.class})
@EnableConfigurationProperties(KafkaProperties.class)
public class RonglyKafkaAutoConfig {

    @Bean
    public RonglyKafkaProduce ronglyKafkaProduce(KafkaTemplate<byte[],byte[]> kafkaTemplate, ObjectMapperJson objectMapperJson){
        return new RonglyKafkaProduce(kafkaTemplate,objectMapperJson);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> consumerFactory,ObjectMapperJson objectMapperJson) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ContainerProperties containerProperties = factory.getContainerProperties();
        factory.setErrorHandler(new LoggingErrorHandler());
        containerProperties.setCommitLogLevel(LogIfLevelEnabled.Level.INFO);
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //过滤不需要的消息
        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {
            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
              Headers headers =  consumerRecord.headers();
                Iterable<Header> headerIterable = headers.headers(RonglyKafkaConstants.MESSAGE_SIGNATURE_KEY);
                Map<String,String> map = MapUtil.newHashMapWithCapacity(1, (float) 0.5);
                headerIterable.forEach((header->{
                    map.put(header.key(),new String(header.value(), StandardCharsets.UTF_8));
                }));
                String MESSAGE_SIGNATURE = map.get(RonglyKafkaConstants.MESSAGE_SIGNATURE_KEY);
                return !Objects.equals(MESSAGE_SIGNATURE,RonglyKafkaConstants.MESSAGE_SIGNATURE);
            }
        });
        factory.setMessageConverter(new RonglyKafkaMessageConverter(objectMapperJson));
        configurer.configure(factory, consumerFactory);
        return factory;
    }
}
