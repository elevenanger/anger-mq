package cn.anger.mq.core;

/**
 * @author : anger
 */
public interface MessageChannel {

    String id();

    void produce(MessageData message);

    MessageData consume(String consumerId);

}
