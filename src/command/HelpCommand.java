package command;

import common.Command;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.CommandRegister;
import manager.MessagePacket;
import util.LoggerUtil;

import java.util.*;

public class HelpCommand implements IServerCommand {
    Map commandList = new HashMap<String, Command>();

    public String getName() {
        return "help";
    }

    public String getDescription() {
        return "Вывести список доступных команд";
    }

    public MessagePacket execute(IServer server, String command) {
        MessagePacket packet = new MessagePacket(LoggerUtil.info("Список доступных команд:"));
        commandList = CommandRegister.getCommands();
        Iterator<Map.Entry> itr = commandList.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            packet.addLines(String.format("%-30s", pair.getKey()) + " " + ((Command) pair.getValue()).getDescription());
        }
        return packet;
    }

}
