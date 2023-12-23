package cn.anger.mq.request;

import cn.anger.mq.core.MessageData;

/**
 * @author : anger
 */
public record ProduceMessageRequest(MessageData message) implements MqRequest { }
