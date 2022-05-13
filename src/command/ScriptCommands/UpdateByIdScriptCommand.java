package command.ScriptCommands;

import common.ScriptCommand;
import interfaces.IServer;
import manager.CollectionManager;
import util.LoggerUtil;

import java.io.BufferedReader;

public class UpdateByIdScriptCommand extends ScriptCommand {
    CollectionManager collectionManager;

    public String getName(){
        return "update_by_id";
    }

    public void execute(IServer server, String command, BufferedReader reader) {
        try {
            collectionManager = server.getCollectionManager();
            String[] args = command.split(" ");
            if (args.length < 2){
                LoggerUtil.negative("Введите ID в скрипте");
            }else if(Integer.parseInt(args[1]) <= collectionManager.getCollection().getID_COUNTER()){
                int id = Integer.parseInt(args[1]);
                collectionManager.getCollection().updateById(id, new AddScriptCommand().
                        executeMain(reader));
            }else {
                LoggerUtil.negative("Элемента с таким ID нет");
            }

        }catch (Exception ex){
            LoggerUtil.negative("Ошибка в скрипте " + ex.getMessage());
        }
    }
}
