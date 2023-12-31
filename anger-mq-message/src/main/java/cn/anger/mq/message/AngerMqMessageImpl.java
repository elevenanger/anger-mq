package cn.anger.mq.message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author : anger
 */
public record AngerMqMessageImpl(String messageId, String producerId, String channelId, byte[] payload) implements AngerMqMessage {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AngerMqMessageImpl that = (AngerMqMessageImpl) o;
        return Objects.equals(messageId, that.messageId) && Objects.equals(producerId, that.producerId) && Objects.equals(channelId, that.channelId) && Arrays.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(messageId, producerId, channelId);
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AngerMqMessageImpl.class.getSimpleName() + "[", "]")
            .add("messageId='" + messageId + "'")
            .add("producerId='" + producerId + "'")
            .add("channelId='" + channelId + "'")
            .add("payload=" + new String(payload, StandardCharsets.UTF_8))
            .toString();
    }
}
