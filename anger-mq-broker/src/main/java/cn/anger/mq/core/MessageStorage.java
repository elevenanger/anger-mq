package cn.anger.mq.core;

/**
 * @author : anger
 */
public interface MessageStorage {

    void storeMessage(String channelId, MessageData messageData);

    MessageData retrieveMessage(String consumerId, String channelId);

}
