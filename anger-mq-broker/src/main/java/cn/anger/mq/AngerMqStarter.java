package cn.anger.mq;

import java.io.IOException;

/**
 * @author : anger
 */
public class AngerMqStarter {

    public static void main(String[] args) throws IOException {
        new AngerMqBootstrap().start();
    }

}
