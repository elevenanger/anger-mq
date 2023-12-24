package cn.anger.mq.server;

import cn.anger.mq.request.ConsumeMessageRequest;
import cn.anger.mq.request.MqRequest;
import cn.anger.mq.request.ProduceMessageRequest;
import cn.anger.mq.response.ConsumeMessageResponse;
import cn.anger.mq.response.MqResponse;
import cn.anger.mq.response.ProduceMessageResponse;

/**
 * @author : anger
 */
public class RequestDispatcher {

    private final RequestHandler<ProduceMessageRequest, ProduceMessageResponse> produceMessageHandler;
    private final RequestHandler<ConsumeMessageRequest, ConsumeMessageResponse> consumeMessageHandler;

    public RequestDispatcher() {
        this.produceMessageHandler = new ProduceMessageHandler();
        this.consumeMessageHandler = new ConsumeMessageHandler();
    }

    public MqResponse dispatchRequest(MqRequest request) {
        return switch (request) {
            case ProduceMessageRequest req -> produceMessageHandler.handle(req);
            case ConsumeMessageRequest req -> consumeMessageHandler.handle(req);
        };
    }

}
