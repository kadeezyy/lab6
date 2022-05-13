package command;

import common.Command;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryCommand implements IServerCommand {
    List<String> commandHistory;

    public String getName() {
        return "history";
    }

    public String getDescription() {
        return "Вывести последние 7 команд";
    }

    public MessagePacket execute(IServer server, String command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("История: "));
        CollectionManager collectionManager = server.getCollectionManager();
        commandHistory = collectionManager.getCommandHistory();
        ArrayList<String> list = new ArrayList<>();
        if (collectionManager.getCollection().getID_COUNTER() > 0) {
            if (collectionManager.getCollection().getID_COUNTER() < 7) {
                for (int i = 0; i < collectionManager.getCollection().getID_COUNTER(); i++) {
                    list.add(commandHistory.get(i));
                }
                packet.addLines(String.valueOf(list));
            } else {
                for (int i = 0; i < 7; i++) {
                    list.add(commandHistory.get(i));
                }
                packet.addLines(String.valueOf(list));
            }
        } else {
            return new MessagePacket(LoggerUtil.negativeAsString("История пустая"));
        }
        return packet;
    }


}
