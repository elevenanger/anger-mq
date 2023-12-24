package cn.anger.mq.client.producer;

import cn.anger.mq.client.config.AngerMqClientProperties;
import cn.anger.mq.client.config.AngerMqProducerProperties;
import cn.anger.mq.message.AngerMqMessage;
import cn.anger.mq.message.MessageBuilder;
import cn.anger.mq.request.ProduceMessageRequest;
import cn.anger.mq.response.ProduceMessageResponse;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author : anger
 */
class AngerMqProducerTest {

    private final AngerMqProducerProperties producerConfig = new AngerMqProducerProperties("anger-mq-client-producer");
    private final AngerMqClientProperties config =
        new AngerMqClientProperties("172.16.249.132", 4216, producerConfig, null);
    private final AngerMqProducer producer = new AngerMqProducer(config);

    @Test
    void sendMessage() {
        AngerMqMessage message =
            MessageBuilder.messageId(UUID.randomUUID().toString())
                .producerId(config.producerConfig().producerId())
                .channelId("anger-mq-client-channel")
                .payload("anger-mq-client-payload".getBytes(StandardCharsets.UTF_8))
                .build();

        ProduceMessageResponse response = producer.produceMessage(new ProduceMessageRequest(message));

        assertNotNull(response);
        assertTrue(response.success());
    }


}