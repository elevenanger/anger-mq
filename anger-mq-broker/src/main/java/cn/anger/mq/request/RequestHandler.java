package cn.anger.mq.request;

/**
 * @author : anger
 */
public interface RequestHandler<T extends MqRequest> {

    void handle(T request);

}
