package interfaces;

import common.Command;
import manager.MessagePacket;

public interface IServerCommand extends Command {
      MessagePacket execute(IServer server, String command);

     default boolean isEnabled(){
        return true;
    }

}
