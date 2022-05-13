package command;

import common.Command;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;


public class ClearCommand implements IServerCommand {
    public String getName(){
        return "clear";
    }

    public String getDescription(){
        return "Очистить коллекцию";
    }


    public MessagePacket execute(IServer server, String command){
        CollectionManager collectionManager = server.getCollectionManager();
        collectionManager.getCollection().clear();
        collectionManager.getCollection().setID_COUNTER(0);
        return new MessagePacket(LoggerUtil.positiveAsString("Коллекция очищена"));
    }
}
