package cn.anger.mq.impl;

import cn.anger.mq.core.MessageBroker;
import cn.anger.mq.core.MessageConsumer;
import cn.anger.mq.core.MessageData;
import cn.anger.mq.core.MessageHandler;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : anger
 */
public class MessageConsumerImpl implements MessageConsumer {

    private final String consumerId;
    private final MessageBroker broker;
    private final AtomicBoolean stop = new AtomicBoolean(false);

    public MessageConsumerImpl(String consumerId, MessageBroker broker) {
        this.consumerId = consumerId;
        this.broker = broker;
    }

    @Override
    public String consumerId() {
        return consumerId;
    }

    @Override
    public void consume(Set<String> channelIds, MessageHandler handler) {
        while (!stop.get())
            channelIds.forEach(channel -> {
                MessageData message = broker.getChannel(channel).consume(consumerId);
                if (message != null)
                    handler.handle(message);
            });
    }

    @Override
    public void shutDown() {
        stop.set(true);
    }
}
