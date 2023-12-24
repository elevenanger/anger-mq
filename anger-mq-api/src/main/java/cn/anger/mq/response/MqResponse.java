package cn.anger.mq.response;

import java.io.Serializable;

/**
 * @author : anger
 */
public interface MqResponse extends Serializable {
    boolean success();
}
