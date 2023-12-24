package cn.anger.mq.server;

import cn.anger.mq.core.MessageBroker;
import cn.anger.mq.core.MessageChannel;
import cn.anger.mq.core.MessageStorage;
import cn.anger.mq.message.AngerMqMessage;

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
    public void produce(AngerMqMessage message) {
        messageStorage.storeMessage(id, message);
    }

    @Override
    public AngerMqMessage consume(String consumerId) {
        return messageStorage.retrieveMessage(consumerId, id);
    }

}
