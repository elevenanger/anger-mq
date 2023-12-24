package cn.anger.mq.client;

/**
 * @author : anger
 */
public class AngerMqClientException extends RuntimeException {
    public AngerMqClientException(String message, Throwable cause) {
        super(message, cause);
    }
    public AngerMqClientException(String message) {
        super(message);
    }
    public AngerMqClientException(Throwable cause) {
        super(cause);
    }
}
