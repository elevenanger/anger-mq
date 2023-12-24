package cn.anger.mq.server;

import cn.anger.mq.core.MessageStorage;
import cn.anger.mq.message.AngerMqMessage;
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
    private static final int QUEUE_SIZE = 10240;
    private static final Logger log = LoggerFactory.getLogger(ArrayBlockingQueueMessageStorage.class);
    private final Map<String, ArrayBlockingQueue<AngerMqMessage>> channelConsumerMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, List<AngerMqMessage>> channelMessageStorage = new ConcurrentHashMap<>();

    @Override
    public void storeMessage(String channelId, AngerMqMessage angerMqMessage) {
        getOrCreateChannelMessageStorage(channelId).add(angerMqMessage);

        channelConsumerMessageQueue.entrySet().stream()
                .filter(entry -> containsChannel(entry.getKey(), channelId))
                .forEach(entry -> entry.getValue().add(angerMqMessage));
    }

    private List<AngerMqMessage> getOrCreateChannelMessageStorage(String channelId) {
        return channelMessageStorage.computeIfAbsent(channelId, k -> {
            List<AngerMqMessage> messages = new CopyOnWriteArrayList<>();
            log.info("channel [{}] 消息存储已创建。", channelId);
            return messages;
        });
    }

    private List<AngerMqMessage> toQueueMessageList(String channelId) {
        List<AngerMqMessage> messages =
            Collections.unmodifiableList(channelMessageStorage.get(channelId));
        return messages.subList(Math.max(0, messages.size() - QUEUE_SIZE), messages.size());
    }

    @Override
    public AngerMqMessage retrieveMessage(String consumerId, String channelId) {
        getOrCreateChannelMessageStorage(channelId);

        final String channelConsumer = channelId + CHANNEL_CONSUMER_SEPARATOR + consumerId;

        return channelConsumerMessageQueue.computeIfAbsent(channelConsumer, k -> {
            ArrayBlockingQueue<AngerMqMessage> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
            queue.addAll(toQueueMessageList(channelId));
            log.info("[{}] 消息队列已创建，消费者 [{}] 开始从消息队列中拉取消息。", k, consumerId);
            return queue;
        }).poll();

    }

    private static boolean containsChannel(String channelConsumer, String channelId) {
        return channelConsumer.split(CHANNEL_CONSUMER_SEPARATOR)[0].equals(channelId);
    }
}
