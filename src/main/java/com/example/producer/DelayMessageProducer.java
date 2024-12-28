package com.example.producer;

import com.alibaba.fastjson.JSON;
import com.example.config.KafkaConfig;
import com.example.model.DelayMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Component
public class DelayMessageProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendDelayMessage(String content, long delayTime) {
        DelayMessage message = new DelayMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setContent(content);
        message.setTopic(KafkaConfig.DELAY_MESSAGE_TOPIC);
        message.setDelayTime(delayTime);
        message.setSendTime(System.currentTimeMillis());
        message.setExpectedExecuteTime(message.getSendTime() + delayTime);

        String messageJson = JSON.toJSONString(message);
        kafkaTemplate.send(KafkaConfig.DELAY_TOPIC, messageJson);
        log.info("发送延迟消息成功，消息ID：{}，延迟时间：{}ms", message.getMessageId(), delayTime);
    }
}
