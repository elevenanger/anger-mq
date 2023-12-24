package cn.anger.mq.client.producer;

import cn.anger.mq.client.AngerMqClient;
import cn.anger.mq.client.config.AngerMqClientProperties;
import cn.anger.mq.request.ProduceMessageRequest;
import cn.anger.mq.response.ProduceMessageResponse;

/**
 * @author : anger
 */
public class AngerMqProducer {

    private final AngerMqClient client;
    private final String producerId;

    public AngerMqProducer(AngerMqClientProperties config) {
        this.client = new AngerMqClient(config);
        this.producerId = config.producerConfig().producerId();
    }

    public ProduceMessageResponse produceMessage(ProduceMessageRequest request) {
        return (ProduceMessageResponse) client.send(request);
    }

    public String producerId() {
        return producerId;
    }
}
