package command;

import Collection.Organization;
import common.Command;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.util.Arrays;
import java.util.List;

public class FilterByAnnualTurnoverCommand implements IServerCommand {

    public String getName() {
        return "filter_by_annual_turnover";
    }

    public String getDescription() {
        return "Вывести все элементы с равным годовым оборотом";
    }

    public MessagePacket execute(IServer server, String  command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Элементы с таким годовым оборотом:"));
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            String[] args = command.split(" ");
            List<Organization> list = collectionManager.getCollection().filter_by_annualTurnover(Float.valueOf(args[1]));
            if (list.isEmpty()) {
                return new MessagePacket(LoggerUtil.negativeAsString("В коллекции нет таких элементов"));
            } else {
//                packet.addLines(LoggerUtil.info("Элементы с таким годовым оборотом:"));
                packet.addLines(String.valueOf(Arrays.asList(list)));
            }
        }catch (ArrayIndexOutOfBoundsException ex1){
            return new MessagePacket(LoggerUtil.negativeAsString("Введите аргумент"));
        }catch(Exception ex){
            return new MessagePacket(LoggerUtil.negativeAsString("Неверно введены данные " + ex.getMessage()));
        }
        return packet;
    }
}
