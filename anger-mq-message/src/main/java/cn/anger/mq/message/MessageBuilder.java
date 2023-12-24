package cn.anger.mq.message;

/**
 * @author : anger
 */
public class MessageBuilder {

    private MessageBuilder () {}
    private String messageId;
    private String producerId;
    private String channelId;
    private byte[] payload;

    public static MessageBuilder messageId(String messageId) {
        MessageBuilder builder = new MessageBuilder();
        builder.messageId = messageId;
        return builder;
    }

    public MessageBuilder producerId(String producerId) {
        this.producerId = producerId;
        return this;
    }

    public MessageBuilder channelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public MessageBuilder payload(byte[] payload) {
        this.payload = payload;
        return this;
    }

    public AngerMqMessage build() {
        return new AngerMqMessageImpl(messageId, producerId, channelId, payload);
    }

}
