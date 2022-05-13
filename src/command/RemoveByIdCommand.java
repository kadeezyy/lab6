package command;

import common.Command;
import interfaces.CommandInterface;
import interfaces.IServer;
import interfaces.IServerCommand;
import manager.CollectionManager;
import manager.MessagePacket;
import util.LoggerUtil;

public class RemoveByIdCommand implements IServerCommand {

    public String getName() {
        return "remove_by_id";
    }

    public String getDescription() {
        return "удалить элемент коллекции по ID";
    }

    public MessagePacket execute(IServer server, String command) {
        try {
            CollectionManager collectionManager = server.getCollectionManager();
            String[] args = command.split(" ");
            int id = Integer.parseInt(String.valueOf(args[1]));
            if( id <= collectionManager.getCollection().getID_COUNTER()) {
                collectionManager.getCollection().removeById(id);
                return new MessagePacket(LoggerUtil.positiveAsString("Элемент был успешно удален из коллекции"));
            }else{
                return new MessagePacket(LoggerUtil.negativeAsString("Элемента с таким ID нет"));
            }
        }catch(ArrayIndexOutOfBoundsException exception){
            return  new MessagePacket(LoggerUtil.negativeAsString("Нужно ввести аргумент"));
        } catch (Exception ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Не удалось удалить элемент (необходимо указать число)"));
        }
    }
}
