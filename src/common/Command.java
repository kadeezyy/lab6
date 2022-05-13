package common;

import interfaces.IServer;
import manager.CollectionManager;
import manager.MessagePacket;

import java.io.Serializable;

public interface Command extends Serializable {
     String getName();

     String getDescription();

     MessagePacket execute(IServer server, String command);

}
