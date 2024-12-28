package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.config.KafkaConfig;
import com.example.model.DelayMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class DelayMessageService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = KafkaConfig.DELAY_TOPIC)
    public void handleDelayMessage(String message) {
        DelayMessage delayMessage = JSON.parseObject(message, DelayMessage.class);
        long currentTime = System.currentTimeMillis();
        long expectedExecuteTime = delayMessage.getExpectedExecuteTime();
        
        // 如果当前时间小于预期执行时间，重新发送到延迟队列
        if (currentTime < expectedExecuteTime) {
            kafkaTemplate.send(KafkaConfig.DELAY_TOPIC, message);
            log.info("消息未到执行时间，重新发送到延迟队列，消息ID：{}", delayMessage.getMessageId());
            return;
        }
        
        // 发送到目标主题
        kafkaTemplate.send(delayMessage.getTopic(), message);
        log.info("延迟消息发送到目标主题，消息ID：{}", delayMessage.getMessageId());
    }

    @KafkaListener(topics = KafkaConfig.DELAY_MESSAGE_TOPIC)
    public void processDelayMessage(String message) {
        DelayMessage delayMessage = JSON.parseObject(message, DelayMessage.class);
        log.info("处理延迟消息，消息ID：{}，内容：{}", delayMessage.getMessageId(), delayMessage.getContent());
        // 这里实现具体的业务逻辑
    }
}
