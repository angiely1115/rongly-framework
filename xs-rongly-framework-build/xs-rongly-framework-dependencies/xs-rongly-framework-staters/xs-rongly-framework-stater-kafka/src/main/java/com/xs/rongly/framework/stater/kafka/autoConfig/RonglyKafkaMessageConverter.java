package com.xs.rongly.framework.stater.kafka.autoConfig;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.xs.rongly.framework.stater.core.base.autoJsonConfig.ObjectMapperJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lvrongzhuan
 * @Description: kafka 消息转换
 * @Date: 2018/11/27 9:50
 * @Version: 1.0
 * modified by:
 */
@Slf4j
public class RonglyKafkaMessageConverter extends MessagingMessageConverter {
    private ObjectMapperJson objectMapperJson;

    public RonglyKafkaMessageConverter(ObjectMapperJson objectMapperJson) {
        this.objectMapperJson = objectMapperJson;
    }

    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        Object value = record.value();
        if (record.value() == null) {
            return KafkaNull.INSTANCE;
        } else {
            String realValue;
            if (value instanceof Bytes) {
                value = ((Bytes)value).get();
            }
            if (value instanceof String) {
                realValue = (String) value;
            } else if (value instanceof byte[]) {
                    realValue = new String((byte[]) value, StandardCharsets.UTF_8);
            } else {
                throw new IllegalStateException("Only String or byte[] supported");
            }
            try {
                RonglyKakfaMessage ronglyKakfaMessage =  objectMapperJson.str2obj(realValue,RonglyKakfaMessage.class);
                log.info("immediately topic is {}, opid is {}, message is {},type:{}", record, ronglyKakfaMessage.getOpid(),
                        RonglyKafkaConstants.createDetailMessage(record, ronglyKakfaMessage.getMessage()),type);
                return ronglyKakfaMessage;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
