package cn.anger.mq.impl;

import cn.anger.mq.core.MessageBroker;
import cn.anger.mq.core.MessageChannel;
import cn.anger.mq.core.MessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : anger
 */
public class MessageBrokerImpl implements MessageBroker {
    
    private static final Logger log = LoggerFactory.getLogger(MessageBrokerImpl.class);
    private final Map<String, MessageChannel> channelMap = new ConcurrentHashMap<>();
    private final MessageStorage messageStorage;
    private static MessageBrokerImpl broker;

    private MessageBrokerImpl(MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
    }

    public static MessageBrokerImpl getBroker() {
        if (broker == null)
            broker = new MessageBrokerImpl(new ArrayBlockingQueueMessageStorage());
        return broker;
    }

    @Override
    public MessageChannel getChannel(String channelId) {
        channelMap.computeIfAbsent(channelId, k -> {
            MessageChannel channel = new MessageChannelImpl(channelId, this);
            log.info("channel [{}] 已创建。", k);
            return channel;
        });
        return channelMap.get(channelId);
    }

    @Override
    public MessageStorage getStorage() {
        return messageStorage;
    }
}
