package cn.anger.mq;

import cn.anger.mq.message.AngerMqMessage;
import cn.anger.mq.message.MessageBuilder;
import cn.anger.mq.request.ProduceMessageRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : anger
 */
class MqProducerTest {

    private final AtomicInteger count = new AtomicInteger(0);

    @Test
    void produceMessageShouldSuccess() throws InterruptedException {
        int threadCount = 100;

        Set<Thread> threads = IntStream.rangeClosed(1, threadCount)
            .mapToObj(i -> Thread.ofVirtual().name("producer-" + i).start(this::produceMessageToMqServer))
            .collect(Collectors.toSet());

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(threadCount, count.get());
    }

    private void produceMessageToMqServer() {
        try (Socket socket = new Socket("localhost", 4216);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            AngerMqMessage message =
                MessageBuilder.messageId(UUID.randomUUID().toString())
                    .channelId("socket-channel")
                    .producerId("test-producer")
                    .payload("test-message".getBytes(StandardCharsets.UTF_8))
                    .build();
            out.writeObject(new ProduceMessageRequest(message));
            System.out.println(in.readObject());
            count.incrementAndGet();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}