package command;

import Collection.Organization;
import common.Command;
import common.InputRequester;
import common.InputUtil;
import interfaces.*;
import manager.CollectionManager;
import manager.CommandPacket;
import manager.MessagePacket;
import sun.rmi.runtime.Log;
import util.LoggerUtil;


public class AddCommand  implements IServerCommand {

    public String getName() {
        return "add";
    }

    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    public MessagePacket execute(IServer server, String command){
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            boolean isSuccessfully = collectionManager.getCollection().add(server.getOrganization());
            if (isSuccessfully) {
                return new MessagePacket(LoggerUtil.info("Элемент был добавлен в коллекцию"));
            } else {
                return new MessagePacket(LoggerUtil.negativeAsString("Элемент не удалось добавить в коллекцию"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return new MessagePacket(LoggerUtil.negativeAsString("Не удалось добавить в коллекцию"));
        }
    }
}
