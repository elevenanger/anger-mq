package cn.anger.mq.core;

/**
 * @author : anger
 */
public interface MessageBroker {

    MessageChannel getChannel(String channelId);

    MessageStorage getStorage();

}
