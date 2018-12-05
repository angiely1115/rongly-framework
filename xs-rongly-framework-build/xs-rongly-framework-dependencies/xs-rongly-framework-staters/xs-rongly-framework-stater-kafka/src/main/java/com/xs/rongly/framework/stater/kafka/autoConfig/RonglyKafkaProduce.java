package com.xs.rongly.framework.stater.kafka.autoConfig;

import com.xs.rongly.framework.stater.core.base.autoJsonConfig.ObjectMapperJson;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import javax.validation.constraints.NotBlank;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lvrongzhuan
 * @Description: kafka生产着
 * @Date: 2018/11/26 17:43
 * @Version: 1.0
 * modified by:
 */
public class RonglyKafkaProduce {
    private KafkaTemplate<byte[],byte[]> kafkaTemplate;
    private ObjectMapperJson objectMapperJson;

    public RonglyKafkaProduce(KafkaTemplate<byte[], byte[]> kafkaTemplate,ObjectMapperJson objectMapperJson) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapperJson = objectMapperJson;
    }

    /**
     * 发送即时消息
     * @param topic
     * @param message
     * @param opid
     */
    public ListenableFuture<SendResult<byte[],byte[]>> sendImmediateMessge(@NotBlank String topic, @NotBlank String message, @NotBlank String opid){
        RonglyKakfaMessage ronglyKakfaMessage = new RonglyKakfaMessage();
        ronglyKakfaMessage.setMessage(message);
        ronglyKakfaMessage.setOpid(opid);
        ProducerRecord<byte[],byte[]> producerRecord = new ProducerRecord<byte[],byte[]>(topic,objectMapperJson.obj2string(ronglyKakfaMessage).getBytes(StandardCharsets.UTF_8));
        producerRecord.headers().add(RonglyKafkaConstants.MESSAGE_OP_ID,opid.getBytes(StandardCharsets.UTF_8))
                .add(RonglyKafkaConstants.MESSAGE_TYPE,RonglyKafkaConstants.IMMEDIATE_MESSAGE_TYPE.getBytes(StandardCharsets.UTF_8))
                .add(RonglyKafkaConstants.MESSAGE_SIGNATURE_KEY,RonglyKafkaConstants.MESSAGE_SIGNATURE.getBytes(StandardCharsets.UTF_8));
        ListenableFuture<SendResult<byte[],byte[]>> listenableFuture = kafkaTemplate.send(producerRecord);
        return listenableFuture;

    }
}
