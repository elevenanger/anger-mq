package cn.anger.mq.client.consumer;

import cn.anger.mq.client.AngerMqClient;
import cn.anger.mq.client.config.AngerMqClientProperties;
import cn.anger.mq.message.AngerMqMessage;
import cn.anger.mq.request.ConsumeMessageRequest;
import cn.anger.mq.response.ConsumeMessageResponse;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author : anger
 */
public class AngerMqConsumer {

    private final AngerMqClient client;
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private final String consumerId;
    private final Set<String> channels;
    private final Consumer<AngerMqMessage> handler;
    private final AtomicInteger rateLimiter = new AtomicInteger(0);
    private final long requestInterval;
    private final int requestLimit;

    public AngerMqConsumer(AngerMqClientProperties config, Consumer<AngerMqMessage> handler) {
        this.client = new AngerMqClient(config);
        consumerId = config.consumerConfig().consumerId();
        channels = config.consumerConfig().channels();
        requestLimit = config.consumerConfig().requestLimit();
        requestInterval = config.consumerConfig().requestInterval();
        this.handler = handler;
    }

    public void consume() {
        while (!stop.get()) {
            channels.forEach(channel -> {
                limitRate();
                ConsumeMessageResponse response =
                    (ConsumeMessageResponse) client.send(new ConsumeMessageRequest(consumerId, channel));
                if (response.success() && response.message() != null)
                    handler.accept(response.message());
            });
        }
    }

    private void limitRate() {
        if (rateLimiter.incrementAndGet() == requestLimit) {
            try {
                Thread.sleep(requestInterval);
                rateLimiter.compareAndSet(requestLimit, 0);
            } catch (InterruptedException e) {
                shutdown();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void shutdown() {
        stop.compareAndSet(false, true);
    }
}
