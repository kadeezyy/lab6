package command.ScriptCommands;

import common.ScriptCommand;
import interfaces.IServer;
import manager.CollectionManager;
import util.LoggerUtil;

import java.io.BufferedReader;

public class AddIfMaxScriptCommand extends ScriptCommand {
    CollectionManager collectionManager;

    public String getName(){
        return "add_if_max";
    }

    public void execute(IServer server, String command, BufferedReader reader){
        collectionManager = server.getCollectionManager();
        boolean isSuccessfullyAdded = collectionManager.getCollection().
                addIfMax(new AddScriptCommand().executeMain(reader));
        if(isSuccessfullyAdded){
            LoggerUtil.positive("Элемент добавлен в коллекцию");
        }else{
            LoggerUtil.negative("Элемент не был добавлен в коллекцию");
        }
    }
}
