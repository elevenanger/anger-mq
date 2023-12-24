package cn.anger.mq;

import cn.anger.mq.server.AngerMqServer;

import java.io.IOException;

/**
 * @author : anger
 */
public class AngerMqServerStarter {

    public static void main(String[] args) throws IOException {
        new AngerMqServer().start();
    }

}
