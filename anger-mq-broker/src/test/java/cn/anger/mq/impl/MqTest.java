package cn.anger.mq.impl;

import cn.anger.mq.core.*;
import cn.anger.mq.util.MessageBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author : anger
 */
class MqTest {

    private static final Logger log = LoggerFactory.getLogger(MessageBrokerImpl.class);
    private final MessageBroker messageBroker = MessageBrokerImpl.getBroker();
    private final String messageChannelId1 = "test-channel-1";
    private final String messageChannelId2 = "test-channel-2";
    private final String messageChannelId3 = "test-channel-3";
    private final Set<String> channels = Set.of(messageChannelId1, messageChannelId2, messageChannelId3);

    @Test
    void produceAndConsumeMessage() throws InterruptedException {
        assertDoesNotThrow(() -> {
            consumeMessage();
            produceMessage();
        });
        Thread.sleep(200);
    }

    private void produceMessage() {
        String producerId = "test-producer";
        final MessageProducer messageProducer = new MessageProducerImpl(messageBroker);
        BiFunction<Integer, String, MessageData> channelMessage = (index, channel) ->
            MessageBuilder
            .messageId(UUID.randomUUID().toString())
            .channelId(channel)
            .producerId(producerId)
            .payload(String.format("%s-test-message-%d", channel, index).getBytes(StandardCharsets.UTF_8))
            .build();

        Thread.ofVirtual().name("producer-t").start(() -> {
            for (int i = 0; i < 10; i++) {
                messageProducer.produce(channelMessage.apply(i, messageChannelId1));
                messageProducer.produce(channelMessage.apply(i, messageChannelId2));
                messageProducer.produce(channelMessage.apply(i, messageChannelId3));
            }
        });

    }

    private void consumeMessage() {
        Thread.ofVirtual().name("consumer-1-t").start(() -> {
            final String consumer1 = "test-consumer-1";
            MessageConsumer messageConsumer1 = new MessageConsumerImpl(consumer1, messageBroker);
            messageConsumer1.consume(channels, message -> log.info("[{}] 消费到消息 [{}]", consumer1, message));
        });

        Thread.ofVirtual().name("consumer-2-t").start(() -> {
            final String consumer2 = "test-consumer-2";
            MessageConsumer messageConsumer2 = new MessageConsumerImpl(consumer2, messageBroker);
            messageConsumer2.consume(channels, message -> log.info("[{}] 消费到消息 [{}]", consumer2, message));
        });
    }

}