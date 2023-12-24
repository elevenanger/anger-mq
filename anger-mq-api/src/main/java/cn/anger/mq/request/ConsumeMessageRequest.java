package cn.anger.mq.request;

/**
 * @author : anger
 */
public record ConsumeMessageRequest(String consumerId, String channel) implements MqRequest{ }
