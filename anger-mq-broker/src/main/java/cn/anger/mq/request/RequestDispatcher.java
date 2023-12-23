package cn.anger.mq.request;

/**
 * @author : anger
 */
public class RequestDispatcher {


    private final RequestHandler<ProduceMessageRequest> produceMessageHandler;

    public RequestDispatcher() {
        this.produceMessageHandler = new ProduceMessageHandler();
    }

    public void dispatchRequest(MqRequest request) {
        switch (request) {
            case ProduceMessageRequest req -> produceMessageHandler.handle(req);
            default -> throw new IllegalStateException("Unexpected value: " + request);
        }
    }

}
