package com.example.model;

import lombok.Data;

@Data
public class DelayMessage {
    /**
     * 消息ID
     */
    private String messageId;
    
    /**
     * 消息主题
     */
    private String topic;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 延迟时间（毫秒）
     */
    private long delayTime;
    
    /**
     * 消息发送时间
     */
    private long sendTime;
    
    /**
     * 消息预期执行时间
     */
    private long expectedExecuteTime;
}
