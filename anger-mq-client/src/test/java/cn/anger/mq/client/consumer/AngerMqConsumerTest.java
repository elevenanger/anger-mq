package cn.anger.mq.client.consumer;

import cn.anger.mq.client.config.AngerMqClientProperties;
import cn.anger.mq.client.config.AngerMqConsumerProperties;
import cn.anger.mq.message.AngerMqMessage;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : anger
 */
class AngerMqConsumerTest {

    private final AngerMqConsumerProperties consumerProperties =
        new AngerMqConsumerProperties("anger-mq-consumer", Set.of("anger-mq-client-channel"), 20, 1000);
    private final AngerMqClientProperties config =
        new AngerMqClientProperties("172.16.249.132", 4216, null, consumerProperties);
    AngerMqConsumer consumer = new AngerMqConsumer(config, this::consumeMessage);

    @Test
    void consumeMessageShouldSuccess() throws InterruptedException {
        Thread t = Thread.ofVirtual().unstarted(consumer::consume);
        t.start();

        Thread.sleep(2000);
        consumer.shutdown();
    }

    private void consumeMessage(AngerMqMessage message) {
        System.out.println(message);
    }

}