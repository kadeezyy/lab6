package command;

import common.Command;
import interfaces.*;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

public class ExitCommand  implements IServerCommand {
    public String getName(){
        return "exit";
    }

    public String getDescription(){
        return "Завершить программу (без сохранения)";
    }

    public MessagePacket execute(IServer server, String command){
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Программа завершилась с сохранением"));
        SaveCommand saveCommand = new SaveCommand();
        saveCommand.execute(server, "save src/files/collectionFile.json");
        return packet;
    }
}
