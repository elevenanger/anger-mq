package cn.anger.mq.core;

import java.util.Set;

/**
 * @author : anger
 */
public interface MessageConsumer {

    String consumerId();

    void consume(Set<String> channelId, MessageHandler handler);

    void shutDown();

}
