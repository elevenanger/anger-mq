package cn.anger.mq.core;

import cn.anger.mq.message.AngerMqMessage;

/**
 * @author : anger
 */
public interface MessageChannel {

    String id();

    void produce(AngerMqMessage message);

    AngerMqMessage consume(String consumerId);

}
