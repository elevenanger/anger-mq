package cn.anger.mq.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author : anger
 */
public class VirtualThreadConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String threadName = Thread.currentThread().getName();

        if (Thread.currentThread().isVirtual())
            threadName = "vt-".concat(threadName);

        return threadName;
    }
}
