package command;

import Collection.Organization;
import common.Command;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

public class ShowCommand  implements IServerCommand {

    public String getName() {
        return "show";
    }

    public String getDescription() {
        return "Вывести элементы коллекции";
    }

    public MessagePacket execute(IServer server, String command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Элементы коллекции:"));
        CollectionManager collectionManager = server.getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            return new MessagePacket(LoggerUtil.negativeAsString("Коллекция пустая"));
        }else {
            int i = 1;
            for (Organization organization : collectionManager.getCollection()) {
                server.send(server.getAddress(), new MessagePacket(organization.toString()));
            }
            return packet;
        }
    }
}
