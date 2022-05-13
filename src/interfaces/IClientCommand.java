package interfaces;

import common.Command;
import manager.MessagePacket;

public interface  IClientCommand extends Command {
     MessagePacket execute2(IClient client, String command);

}
