package command;

import Collection.Organization;
import common.Command;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FilterByFullNameCommand implements IServerCommand {

    public String getName() {
        return "filter_contains_full_name";
    }

    public String getDescription() {
        return "Вывести элементы, имеющие такую же фамилию";
    }

    public MessagePacket execute(IServer server, String command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Элементы с такой фамилией:"));
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            String[] args = command.split(" ");
            List<Organization> list = collectionManager.getCollection().filter_by_fullName(args[1]);
            if (list.isEmpty()) {
                return new MessagePacket(LoggerUtil.negativeAsString("В коллекции нет элементов"));
            } else {
                packet.addLines(String.valueOf(Arrays.asList(list)));
            }
        } catch (ArrayIndexOutOfBoundsException ex1) {
            return new MessagePacket(LoggerUtil.negativeAsString("Введите аргумент"));
        } catch (Exception ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Неверно введены данные " + ex.getMessage()));
        }
        return packet;
    }
}
