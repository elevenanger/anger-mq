package cn.anger.mq.server;

import cn.anger.mq.request.MqRequest;
import cn.anger.mq.response.MqResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : anger
 */
public class AngerMqServer {

    private static final Logger log = LoggerFactory.getLogger(AngerMqServer.class);
    private static final int port = 4216;
    private final RequestDispatcher dispatcher = new RequestDispatcher();
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        log.info("Started AngerMq on port [{}]", port);

        while (!Thread.interrupted()) {
            Socket clientSocket = serverSocket.accept();
            activeConnections.incrementAndGet();
            log.info("Accepted connection from [{}], current connections [{}].",
                clientSocket.getRemoteSocketAddress(),
                activeConnections.get());

            Thread.ofVirtual().name("mq-server").start(() -> {
                try(ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
                    out.writeObject(process(in));
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        log.info("connection closed, current connections [{}].", activeConnections.decrementAndGet());
                        clientSocket.close();
                    } catch (IOException e) {
                        log.info("close socket error.");
                    }
                }
            });
        }
    }

    private MqResponse process(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream.readObject() instanceof MqRequest request)
            return dispatcher.dispatchRequest(request);
        throw new IllegalAccessError("Invalid request");
    }

    public void shutDown() throws IOException {
        serverSocket.close();
    }

}
