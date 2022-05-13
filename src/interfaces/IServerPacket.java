package interfaces;

import java.net.SocketAddress;

public interface IServerPacket extends IPacket {
    void handleOnServer(IServer server, SocketAddress clientAddress);
}
