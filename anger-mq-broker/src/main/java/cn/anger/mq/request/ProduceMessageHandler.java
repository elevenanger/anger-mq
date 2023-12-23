package cn.anger.mq.request;

import cn.anger.mq.core.MessageData;
import cn.anger.mq.impl.MessageBrokerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : anger
 */
public class ProduceMessageHandler implements RequestHandler<ProduceMessageRequest> {

    private static final Logger log = LoggerFactory.getLogger(ProduceMessageHandler.class);

    @Override
    public void handle(ProduceMessageRequest request) {
        MessageData messageData = request.message();
        log.info("收到生产消息请求，开始处理消息 [{}]。", messageData);
        MessageBrokerImpl.getBroker().getChannel(messageData.channelId()).produce(messageData);
    }
}
