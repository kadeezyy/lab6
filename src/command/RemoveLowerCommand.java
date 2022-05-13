package command;

import Collection.Organization;
import common.Command;
import common.InputRequester;
import common.InputUtil;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

public class RemoveLowerCommand implements IServerCommand {

    public String getName() {
        return "remove_lower";
    }

    public String getDescription() {
        return "удалить из коллекции элементы, меньшие, чем заданный";
    }

    public MessagePacket execute(IServer server, String command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Введите данные элемента, с которым нужно сравнивать"));
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            collectionManager.getCollection().removeLower(server.getOrganization());
            packet.addLines(LoggerUtil.positiveAsString("Элементы успешно удалены"));
        } catch (Exception ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Ошибка! " + ex.getMessage()));
        }
        return packet;
    }
}
