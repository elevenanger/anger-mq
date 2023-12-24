package cn.anger.mq.server;

import cn.anger.mq.request.MqRequest;
import cn.anger.mq.response.MqResponse;

/**
 * @author : anger
 */
public interface RequestHandler<T extends MqRequest, R extends MqResponse> {

    R handle(T request);

}
