
import interfaces.IClientPacket;
import util.SerializationUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ResponseHandler extends Thread {
    Client client;
    Selector selector;

    public ResponseHandler(Client client, Selector selector) {
        this.client = client;
        this.selector = selector;
        start();
    }

    public void run() {
        try {
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = (this.selector).selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isReadable()) {
                        continue;
                    }
                    handleResponse(key);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
        }
    }

    public void handleResponse(SelectionKey key) throws IOException, ClassNotFoundException {
        client.getResponseReceived().set(true);
        ByteBuffer buffer = ByteBuffer.allocate(32768);
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.receive(buffer);
        IClientPacket packet = (IClientPacket) SerializationUtil.deserialize(buffer.array());
        packet.handleOnClient(client);
    }
}
