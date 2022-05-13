package interfaces;

import manager.CollectionManager;

public interface CommandInterface {
    String getName();
    String getDescription();
    void execute(CollectionManager collectionManager, String command);
}
