package cn.anger.mq.impl;

import cn.anger.mq.core.MessageBroker;
import cn.anger.mq.core.MessageData;
import cn.anger.mq.core.MessageProducer;

/**
 * @author : anger
 */
public class MessageProducerImpl implements MessageProducer {

    private final MessageBroker broker;

    public MessageProducerImpl(MessageBroker broker) {
        this.broker = broker;
    }

    @Override
    public void produce(MessageData message) {
        broker.getChannel(message.channelId()).produce(message);
    }

}
