package cn.anger.mq.client;

import cn.anger.mq.client.config.AngerMqClientProperties;
import cn.anger.mq.request.MqRequest;
import cn.anger.mq.response.MqResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author : anger
 */
public class AngerMqClient {

    private final AngerMqClientProperties config;

    private final Executor exec = Executors.newVirtualThreadPerTaskExecutor();

    public AngerMqClient(AngerMqClientProperties config) {
        this.config = config;
    }

    public MqResponse send(MqRequest request) {
        try {
            return CompletableFuture.supplyAsync(() -> sendRequest(request), exec).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AngerMqClientException(e);
        } catch (Exception e) {
            throw new AngerMqClientException(e);
        }
    }

    private MqResponse sendRequest(MqRequest request) {
        try (Socket socket = new Socket(config.host(), config.port());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
            out.writeObject(request);
            return (MqResponse) in.readObject();
        } catch (Exception e) {
            throw new AngerMqClientException("发送请求异常： ", e);
        }
    }

}
