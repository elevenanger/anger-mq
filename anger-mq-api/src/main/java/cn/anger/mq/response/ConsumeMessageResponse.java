package cn.anger.mq.response;

import cn.anger.mq.message.AngerMqMessage;

/**
 * @author : anger
 */
public record ConsumeMessageResponse(boolean success, AngerMqMessage message) implements MqResponse {
}
