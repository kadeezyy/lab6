package command;

import command.ScriptCommands.AddIfMaxScriptCommand;
import command.ScriptCommands.AddScriptCommand;
import command.ScriptCommands.RemoveLowerScriptCommand;
import command.ScriptCommands.UpdateByIdScriptCommand;
import common.Command;
import common.ScriptCommand;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.CommandRegister;
import manager.MessagePacket;
import util.LoggerUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ExecuteScriptCommand implements IServerCommand {
    Map<String, Command> map1 = new HashMap<>(15);
    Map<String, ScriptCommand> map2 = new HashMap<>(15);


    public String getName() {
        return "execute_script";
    }

    public String getDescription() {
        return "Считать и исполнить скрипт из файла";
    }

    public MessagePacket execute(IServer server, String command) {
        String[] args = command.split(" ");
        MessagePacket packet = new MessagePacket(LoggerUtil.positiveAsString("Начало исполнения скрипта " + args[1]));
        CollectionManager collectionManager = server.getCollectionManager();
        if (args.length < 2) {
//            System.out.println(args.length);
//            System.out.println(Arrays.asList(args));
            return new MessagePacket(LoggerUtil.negativeAsString("Необходимо указать путь к файлу со скриптом"));
        } else {
            if (!collectionManager.getScriptInProcess().contains(args[1])) {
                File file = new File(args[1]);
                collectionManager.getScriptInProcess().add(args[1]);
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        map2 = CommandRegister.getScriptCOMMANDS();
                        map1 = CommandRegister.getCommands();
                        ConcurrentHashMap<String, ScriptCommand> cMap2 = new ConcurrentHashMap<>(map2);
                        Arrays.asList(
                                new AddIfMaxCommand(),
                                new AddCommand(),
                                new RemoveLowerCommand(),
                                new UpdateByIdCommand()
                        ).forEach(command1 -> map1.remove(command1.getName()));
                        ConcurrentHashMap<String, Command> cMap = new ConcurrentHashMap<>(map1);
                        boolean found = false;

                        for (String key2 : cMap2.keySet()) {
                            if (key2.equals(line.trim().split(" ")[0])) {
                                collectionManager.getCommandHistory().add(line.trim().split(" ")[0]);
                                map2.get(key2).execute(server, line, reader);
                                found = true;
                            }
                        }
                        for (String key1 : cMap.keySet()) {
                            if (key1.equals(line.trim().split(" ")[0])) {
                                collectionManager.getCommandHistory().add(line.trim().split(" ")[0]);
                                Command thing = map1.get(key1);
                                packet.addLines((thing.execute(server, line)).getLines());
                                found = true;
                            }
                        }
                        if (!found) {
                            return new MessagePacket(LoggerUtil.negativeAsString("Заданной в скрипте команды нет"));
                        }
                    }
                    collectionManager.getScriptInProcess().remove(args[1]);
                } catch (IOException ex) {
                    collectionManager.getScriptInProcess().remove(args[1]);
                    return new MessagePacket(LoggerUtil.negativeAsString("Не удалось исполнить скрипт " + ex.getMessage()));
                }
            } else {
                return new MessagePacket(LoggerUtil.negativeAsString("Данный скрипт уже выполняется"));
            }
        }
        return packet;
    }
}
