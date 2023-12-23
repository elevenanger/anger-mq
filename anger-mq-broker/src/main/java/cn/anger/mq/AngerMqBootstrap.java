package cn.anger.mq;

import cn.anger.mq.request.MqRequest;
import cn.anger.mq.request.RequestDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;

/**
 * @author : anger
 */
public class AngerMqBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AngerMqBootstrap.class);
    private static final int port = 4216;
    private final RequestDispatcher dispatcher = new RequestDispatcher();
    private final ThreadFactory factory = Thread.ofVirtual().name("mq-thread").factory();
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        log.info("Started AngerMq on port [{}]", port);

        while (!Thread.interrupted()) {
            Socket clientSocket = serverSocket.accept();
            log.info("Accepted connection from [{}] ", clientSocket.getRemoteSocketAddress());
            factory.newThread(() -> {
                try(ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                    process(in);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void process(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream.readObject() instanceof MqRequest request)
            dispatcher.dispatchRequest(request);
    }

    public void shutDown() throws IOException {
        serverSocket.close();
    }

}
