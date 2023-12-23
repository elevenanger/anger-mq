package cn.anger.mq;

import cn.anger.mq.core.MessageData;
import cn.anger.mq.request.ProduceMessageRequest;
import cn.anger.mq.util.MessageBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author : anger
 */
class AngerMqBootstrapTest {

    @Test
    void produceMessageShouldSuccess() {
        assertDoesNotThrow(this::produceMessageToMqServer);
    }

    private void produceMessageToMqServer() throws IOException {
        try (Socket socket = new Socket("localhost", 4216);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            MessageData message =
                MessageBuilder.messageId(UUID.randomUUID().toString())
                    .channelId("socket-channel")
                    .producerId("test-producer")
                    .payload("test-message".getBytes(StandardCharsets.UTF_8))
                    .build();
            out.writeObject(new ProduceMessageRequest(message));
        }
    }

}