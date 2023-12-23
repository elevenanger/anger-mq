package cn.anger.mq.core;

import java.io.Serializable;

/**
 * @author : anger
 */
public interface MessageData extends Serializable {

    String messageId();
    String producerId();
    String channelId();
    byte[] payload();

}
