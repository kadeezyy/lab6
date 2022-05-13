import common.InputRequester;
import manager.CollectionManager;
import util.EnvVariablesUtil;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(EnvVariablesUtil.getPort());
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.readCollectionFromFile();
        server.setCollectionManager(collectionManager);
        server.start();
    }
}
