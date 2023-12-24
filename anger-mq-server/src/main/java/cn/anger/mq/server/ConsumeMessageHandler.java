package cn.anger.mq.server;

import cn.anger.mq.message.AngerMqMessage;
import cn.anger.mq.request.ConsumeMessageRequest;
import cn.anger.mq.response.ConsumeMessageResponse;

/**
 * @author : anger
 */
public class ConsumeMessageHandler implements RequestHandler<ConsumeMessageRequest, ConsumeMessageResponse> {

    @Override
    public ConsumeMessageResponse handle(ConsumeMessageRequest request) {
        final AngerMqMessage message =
            MessageBrokerImpl.getBroker().getChannel(request.channel()).consume(request.consumerId());
        return new ConsumeMessageResponse(true, message);
    }
}
