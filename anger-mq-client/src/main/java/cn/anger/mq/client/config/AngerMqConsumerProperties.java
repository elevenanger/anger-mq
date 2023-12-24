package cn.anger.mq.client.config;

import java.util.Set;

/**
 * @author : anger
 */
public record AngerMqConsumerProperties(String consumerId, Set<String> channels, int requestLimit, long requestInterval) {
}
