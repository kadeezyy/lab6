package command;

import Collection.Organization;
import common.Command;
import common.InputRequester;
import common.InputUtil;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UpdateByIdCommand implements IServerCommand {

    public String getName() {
        return "update_id";
    }

    public String getDescription() {
        return "Обновить значение элемента коллекции по заданному ID";
    }

    public MessagePacket execute(IServer server, String command) {
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            int id = Integer.parseInt(command.split(" ")[1]);
            if (id <= collectionManager.getCollection().getID_COUNTER()) {
                collectionManager.getCollection().updateById(id, server.getOrganization());
                return new MessagePacket(LoggerUtil.positiveAsString("Элемент был успешно обновлен"));
            } else {
                return new MessagePacket(LoggerUtil.negativeAsString("Элемента с таким ID нет"));
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Нужно ввести аргумент"));
        } catch (Exception ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Неверно введены данные " + ex.getMessage()));
        }
    }
}
