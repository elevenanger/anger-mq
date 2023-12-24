package cn.anger.mq.request;

import cn.anger.mq.message.AngerMqMessage;

/**
 * @author : anger
 */
public record ProduceMessageRequest(AngerMqMessage message) implements MqRequest { }
