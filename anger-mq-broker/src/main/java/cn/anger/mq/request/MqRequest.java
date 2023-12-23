package cn.anger.mq.request;

import java.io.Serializable;

/**
 * @author : anger
 */
public sealed interface MqRequest extends Serializable permits ProduceMessageRequest, ConsumeMessageRequest {
}
