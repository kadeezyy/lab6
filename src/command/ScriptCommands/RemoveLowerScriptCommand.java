package command.ScriptCommands;

import Collection.Organization;
import command.AddCommand;
import common.ScriptCommand;
import interfaces.IServer;
import manager.CollectionManager;
import util.LoggerUtil;

import java.io.BufferedReader;

public class RemoveLowerScriptCommand extends ScriptCommand {
    CollectionManager collectionManager;
    public String getName(){
        return "remove_lower";
    }
    public void execute(IServer server, String command, BufferedReader reader){
        try {
            collectionManager = server.getCollectionManager();
            Organization organization = new AddScriptCommand().executeMain(reader);
            collectionManager.getCollection().removeLower(organization);
            LoggerUtil.positive("Элементы успешно удалены");
        }catch (Exception ex){
            LoggerUtil.negative("Ошибка! "+ ex.getMessage());
        }
    }
}
