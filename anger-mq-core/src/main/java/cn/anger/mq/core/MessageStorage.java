package cn.anger.mq.core;

import cn.anger.mq.message.AngerMqMessage;

/**
 * @author : anger
 */
public interface MessageStorage {

    void storeMessage(String channelId, AngerMqMessage angerMqMessage);

    AngerMqMessage retrieveMessage(String consumerId, String channelId);

}
