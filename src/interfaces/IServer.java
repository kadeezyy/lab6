package interfaces;

import Collection.Organization;
import manager.CollectionManager;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public interface IServer {
    void send(SocketAddress address, IClientPacket packet);
    CollectionManager getCollectionManager();
    void setOrganization(Organization organization);
    Organization getOrganization();
    SocketAddress getAddress();
}
