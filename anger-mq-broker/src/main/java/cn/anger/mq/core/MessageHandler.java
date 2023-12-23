package cn.anger.mq.core;

/**
 * @author : anger
 */
public interface MessageHandler {

    void handle(MessageData messageData);

}
