package cn.anger.mq.impl;

import cn.anger.mq.core.MessageData;
import cn.anger.mq.core.MessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : anger
 */
public class ArrayBlockingQueueMessageStorage implements MessageStorage {

    private static final String CHANNEL_CONSUMER_SEPARATOR = "#";
    private static final int QUEUE_SIZE = 1024;
    private static final Logger log = LoggerFactory.getLogger(ArrayBlockingQueueMessageStorage.class);
    private final Map<String, ArrayBlockingQueue<MessageData>> channelConsumerMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, List<MessageData>> channelMessageStorage = new ConcurrentHashMap<>();

    @Override
    public void storeMessage(String channelId, MessageData messageData) {
        getOrCreateChannelMessageStorage(channelId).add(messageData);

        channelConsumerMessageQueue.entrySet().stream()
                .filter(entry -> containsChannel(entry.getKey(), channelId))
                .forEach(entry -> entry.getValue().add(messageData));
    }

    private List<MessageData> getOrCreateChannelMessageStorage(String channelId) {
        return channelMessageStorage.computeIfAbsent(channelId, k -> {
            List<MessageData> messages = new CopyOnWriteArrayList<>();
            log.info("[{}] 消息存储已创建。", channelId);
            return messages;
        });
    }

    private List<MessageData> toQueueMessageList(String channelId) {
        List<MessageData> messages =
            Collections.unmodifiableList(channelMessageStorage.get(channelId));
        return messages.subList(Math.max(0, messages.size() - QUEUE_SIZE), messages.size());
    }

    @Override
    public MessageData retrieveMessage(String consumerId, String channelId) {
        getOrCreateChannelMessageStorage(channelId);

        final String channelConsumer = channelId + CHANNEL_CONSUMER_SEPARATOR + consumerId;

        return channelConsumerMessageQueue.computeIfAbsent(channelConsumer, k -> {
            ArrayBlockingQueue<MessageData> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
            queue.addAll(toQueueMessageList(channelId));
            log.info("[{}] 消息队列已创建，消费者 [{}] 开始从[{}] 的消息存储中拉取消息。", k, consumerId, channelId);
            return queue;
        }).poll();

    }

    private static boolean containsChannel(String channelConsumer, String channelId) {
        return channelConsumer.split(CHANNEL_CONSUMER_SEPARATOR)[0].equals(channelId);
    }
}
