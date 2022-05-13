import common.InputRequester;
import interfaces.IClient;
import interfaces.IServerPacket;
import util.LoggerUtil;
import util.SerializationUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client implements IClient {
    DatagramChannel channel;
    final InetSocketAddress socketAddress;
    Selector selector;
    InputRequester inputRequester;
    AtomicBoolean responseReceived = new AtomicBoolean(false);

    public Client(String address, int port) {
        socketAddress = new InetSocketAddress(address, port);
        try {
            channel = DatagramChannel.open();
            channel.bind(null);
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException ex) {
            LoggerUtil.negative("Не удалось запустить программу. Отключение");
            System.exit(0);
        }
    }

    public void send(IServerPacket packet) {
        try {
            channel.send(ByteBuffer.wrap(SerializationUtil.serialize(packet)), socketAddress);
            Thread.sleep(250);

            if (!responseReceived.get()) {
                waitForResponse(packet);
            } else {
                responseReceived.set(false);
            }
        } catch (IOException | InterruptedException ex) {
            LoggerUtil.negative("Не удалось отправить данные серверу" );
            ex.printStackTrace();
        }
    }

    public void registerResponseHandler() {
        new ResponseHandler(this, selector);
    }

    public void waitForResponse(IServerPacket packet) throws IOException {
        int attempts = 1;
        byte[] bytes = SerializationUtil.serialize(packet);
        while (!responseReceived.get()) {
            LoggerUtil.infoAsString("Ожидание ответа со стороны сервера . . . ");
            try {
                channel.send(ByteBuffer.wrap(bytes), socketAddress);
                Thread.sleep(1000*attempts++);
            } catch (InterruptedException ignored) {
            }
        }
        responseReceived.set(false);
    }

    public AtomicBoolean getResponseReceived() {
        return responseReceived;
    }

    public void   setInputRequester(InputRequester inputRequester){
        this.inputRequester = inputRequester;
    }
}
