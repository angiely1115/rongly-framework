package com.xs.rongly.framework.stater.kafka.autoConfig;

import lombok.Data;

import java.util.UUID;

/**
 * @Author: lvrongzhuan
 * @Description: kafka发送消息
 * @Date: 2018/11/26 17:45
 * @Version: 1.0
 * modified by:
 */
@Data
public class RonglyKakfaMessage {
    private String message;
    private String opid;

    final private String randomStr = UUID.randomUUID().toString();
    final private String messageType = RonglyKafkaConstants.IMMEDIATE_MESSAGE_TYPE;
    final private String signature = RonglyKafkaConstants.MESSAGE_SIGNATURE;
}
