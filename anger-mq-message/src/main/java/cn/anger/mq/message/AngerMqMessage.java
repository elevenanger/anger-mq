package cn.anger.mq.message;

import java.io.Serializable;

/**
 * @author : anger
 */
public interface AngerMqMessage extends Serializable {

    String messageId();
    String producerId();
    String channelId();
    byte[] payload();

}
