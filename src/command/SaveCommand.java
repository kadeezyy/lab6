package command;


import common.Command;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

public class SaveCommand implements IServerCommand {
    public String getName() {
        return "save";
    }

    public String getDescription() {
        return "Сохранить коллекцию в файл";
    }

    public MessagePacket execute(IServer server, String command) {
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            String[] args = command.split(" ");
            return collectionManager.writeCollectionToFile(args[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Необходимо ввести путь к файлу"));
        }
    }
}
