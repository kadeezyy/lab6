package command;

import Collection.Organization;
import Collection.OrganizationCollection;
import common.Command;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.text.SimpleDateFormat;

public class InfoCommand implements IServerCommand {

    public String getName(){
        return "info";
    }

    public String getDescription(){
        return "Вывести информацию о коллекции";
    }

    public MessagePacket execute(IServer server, String command){
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Информация о коллекции:"));
        CollectionManager collectionManager = server.getCollectionManager();
        OrganizationCollection collection = collectionManager.getCollection();
        packet.addLines("Тип: " + collection.getClass().getSuperclass().getName());
        packet.addLines("Дата инициализации: " + new SimpleDateFormat().format(collection.getInitDate()));
        packet.addLines("Количество элементов: " + collection.size());
        return packet;
    }
}
