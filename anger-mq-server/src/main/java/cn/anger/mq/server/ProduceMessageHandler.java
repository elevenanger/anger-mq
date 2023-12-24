package cn.anger.mq.server;

import cn.anger.mq.request.ProduceMessageRequest;
import cn.anger.mq.message.AngerMqMessage;
import cn.anger.mq.response.ProduceMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : anger
 */
public class ProduceMessageHandler implements RequestHandler<ProduceMessageRequest, ProduceMessageResponse> {

    private static final Logger log = LoggerFactory.getLogger(ProduceMessageHandler.class);

    @Override
    public ProduceMessageResponse handle(ProduceMessageRequest request) {
        AngerMqMessage angerMqMessage = request.message();
        log.info("收到生产消息请求，开始处理消息 [{}]。", angerMqMessage);
        MessageBrokerImpl.getBroker().getChannel(angerMqMessage.channelId()).produce(angerMqMessage);
        return new ProduceMessageResponse(true);
    }
}
