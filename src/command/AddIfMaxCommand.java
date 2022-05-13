package command;

import Collection.Organization;
import Collection.OrganizationCollection;
import common.Command;
import common.InputRequester;
import common.InputUtil;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AddIfMaxCommand implements IServerCommand {
    InputRequester inputRequester;

    public String getName() {
        return "add_if_max";
    }

    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента коллекции";
    }

    public MessagePacket execute(IServer server, String command) {
        try {
            inputRequester = new InputRequester(new BufferedReader(new InputStreamReader(System.in)));
            CollectionManager collectionManager = server.getCollectionManager();
            LoggerUtil.infoAsString("Введите данные элемента, с которым нужно сравнить");
            boolean isSuccessfullyAdded = collectionManager.getCollection().addIfMax(server.getOrganization());
            if (isSuccessfullyAdded) {
                return new MessagePacket(LoggerUtil.positiveAsString("Элемент успешно добавлен в коллекцию"));
            } else {
                return new MessagePacket(LoggerUtil.negativeAsString("Элемент не удалось добавить в коллекцию"));
            }
        }catch(Exception ex){
            return new MessagePacket(LoggerUtil.negativeAsString("Неверно введены данные"));
        }
    }
}
