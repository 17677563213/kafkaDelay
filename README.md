# Spring Boot整合Kafka实现延迟消息

## 项目介绍
本项目是一个基于Spring Boot和Kafka实现的延迟消息处理系统。通过该系统，可以实现消息的延时投递，适用于定时任务、延时提醒等业务场景。

## 技术栈
- Spring Boot 2.7.0
- Apache Kafka
- Lombok
- FastJSON

## 核心功能
1. 延迟消息发送
2. 消息延迟投递
3. 消息可靠性保证
4. RESTful API接口

## 系统架构
系统主要包含两个Topic：
- `delay_topic`: 延迟消息暂存Topic，用于存储尚未到达执行时间的消息
- `delay_message_topic`: 实际消息处理Topic，用于处理到达执行时间的消息

### 消息处理流程
1. 生产者发送延迟消息到`delay_topic`
2. 消费者监听`delay_topic`，检查消息是否到达执行时间
3. 如果未到执行时间，消息重新发送到`delay_topic`
4. 如果到达执行时间，消息转发到`delay_message_topic`
5. 业务消费者处理`delay_message_topic`中的消息

## 主要组件
1. **DelayMessage**: 延迟消息实体类
2. **DelayMessageProducer**: 消息生产者
3. **DelayMessageService**: 延迟消息处理服务
4. **DelayMessageController**: REST接口控制器

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.0+
- Kafka 2.x+

### 配置说明
配置文件位于`src/main/resources/application.yml`：
```yaml
server:
  port: 8081

spring:
  kafka:
    bootstrap-servers: localhost:9092
    # 其他Kafka配置...
```

### 启动步骤
1. 确保Kafka服务已启动
2. 修改application.yml中的Kafka配置
3. 运行DelayMessageApplication启动应用

### API使用
发送延迟消息：
```http
POST http://localhost:8081/api/delay/send?content=test message&delayTime=5000
```
参数说明：
- content: 消息内容
- delayTime: 延迟时间（毫秒）

## 性能优化
1. 使用多分区（默认3个）提高并行处理能力
2. 批量发送消息提高吞吐量
3. 合理配置消息缓冲区大小

## 注意事项
1. 确保Kafka服务可用
2. 合理设置延迟时间
3. 注意监控消息处理情况
4. 建议在生产环境配置多副本以提高可用性

## 项目维护
@author wxy
