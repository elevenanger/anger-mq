package cn.anger.mq.client.config;

import java.util.Set;

/**
 * @author : anger
 */
public record AngerMqClientProperties(String host, int port,
                                      AngerMqProducerProperties producerConfig,
                                      AngerMqConsumerProperties consumerConfig) {
}
