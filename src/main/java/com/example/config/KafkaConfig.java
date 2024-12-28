package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    
    public static final String DELAY_TOPIC = "delay_topic";
    public static final String DELAY_MESSAGE_TOPIC = "delay_message_topic";
    
    /**
     * 创建Kafka延迟消息Topic配置
     * 
     * @return NewTopic Kafka主题配置对象
     * 
     * 配置说明：
     * - name: 设置Topic名称为delay_topic
     * - partitions: 设置分区数为3，用于提高并行处理能力
     * - replicas: 设置副本数为1，用于数据备份
     * 
     * 该Topic用于存储尚未到达执行时间的延迟消息，
     * 消息会在此Topic中循环，直到达到预期执行时间
     */
    @Bean
    public NewTopic delayTopic() {
        return TopicBuilder.name(DELAY_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    /**
     * 创建Kafka延迟消息目标Topic配置
     * 
     * @return NewTopic Kafka主题配置对象
     * 
     * 配置说明：
     * - name: 设置Topic名称为delay_message_topic
     * - partitions: 设置分区数为3，用于提高并行处理能力
     * - replicas: 设置副本数为1，用于数据备份
     * 
     * 该Topic用于接收已经到达执行时间的延迟消息，
     * 消费者会监听此Topic来处理最终的业务逻辑
     */
    @Bean
    public NewTopic delayMessageTopic() {
        return TopicBuilder.name(DELAY_MESSAGE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
