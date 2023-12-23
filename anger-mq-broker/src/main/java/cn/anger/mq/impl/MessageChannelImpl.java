package cn.anger.mq.impl;

import cn.anger.mq.core.MessageBroker;
import cn.anger.mq.core.MessageChannel;
import cn.anger.mq.core.MessageData;
import cn.anger.mq.core.MessageStorage;

/**
 * @author : anger
 */
public class MessageChannelImpl implements MessageChannel {
    private final String id;
    private final MessageStorage messageStorage;

    public MessageChannelImpl(String id,
                              MessageBroker messageBroker) {
        this.id = id;
        this.messageStorage = messageBroker.getStorage();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void produce(MessageData message) {
        messageStorage.storeMessage(id, message);
    }

    @Override
    public MessageData consume(String consumerId) {
        return messageStorage.retrieveMessage(consumerId, id);
    }

}
