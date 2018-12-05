package com.xs.rongly.framework.stater.kafka.autoConfig;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Author: lvrongzhuan
 * @Description: kafka常量配置
 * @Date: 2018/11/26 17:36
 * @Version: 1.0
 * modified by:
 */
public interface RonglyKafkaConstants {
    String HN_DELAY_TOPIC = "rongly_delay_topic";

    String MESSAGE_TYPE = "message_type";
    String DELAY_MESSAGE_TYPE = "delay_type";
    String IMMEDIATE_MESSAGE_TYPE = "immediate_type";

    String MESSAGE_SIGNATURE_KEY = "signature";
    String MESSAGE_SIGNATURE = "Jw@x2M9!d";

    String MESSAGE_OP_ID = "opid";
    String MESSAGE_DELAY_TIME = "delay_time";

    int MAX_CONTENT_LOGGED = 1024;

    /**
     * 创建消息详情
     * @param record
     * @param message
     * @return
     */
    static String createDetailMessage(ConsumerRecord<?, ?> record, String message) {
        return String
                .format("topic = %s, partition = %s, offset = %s, timestamp = %s, message = %s",
                        record.topic(), record.partition(), record.offset(), record.timestamp(),
                        message);
    }

    static String toDisplayString(String original, int maxCharacters) {
        if (original.length() <= maxCharacters) {
            return original;
        }
        return original.substring(0, maxCharacters) + "...";
    }

    /**
     * 获取消息信息
     * @param value
     * @param prefix
     * @param record
     * @return
     */
    static String getMessage(byte[] value, String prefix, ConsumerRecord<?, ?> record) {
        String message = new String(value, StandardCharsets.UTF_8);
        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        sb.append("; topic is ").append(record.topic());
        sb.append("; partition is ").append(record.partition());
        sb.append("; offset is ").append(record.offset());
        if (record.headers() != null) {
            sb.append("; header is ");
            Arrays.stream(record.headers().toArray()).forEach(h -> sb.append(h.key()).append("=")
                    .append(new String(h.value(), StandardCharsets.UTF_8)));
        }
        sb.append("; message is ").append(toDisplayString(message, 1024));
        return sb.toString();
    }
}
