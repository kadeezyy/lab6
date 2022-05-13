package manager;

import common.Command;
import common.InputRequester;
import interfaces.*;
import util.LoggerUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    private Map<String, Command> map = new HashMap<>(15);
    private IClient client;
    private InputRequester inputRequester;

    public CommandManager(IClient client, InputRequester inputRequester){
        this.client = client;
        this.inputRequester = inputRequester;
    }


    public void findCommand(String command) {
        String[] args = command.split(" ");
        map = CommandRegister.getCommands();
//        ConcurrentHashMap<String, Command> cMap = new ConcurrentHashMap<>(map);

        if (map.keySet().contains(args[0])) {
            client.send(new CommandPacket(command));
        } else {
            LoggerUtil.negative("Такой команды нет");
            LoggerUtil.infoAsString("Напишите \"help\" , чтобы увидеть список команд");
            return;
        }

        if(args[0].equals("exit")){
            System.exit(0);
        }
    }
}
