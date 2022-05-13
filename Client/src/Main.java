import common.InputRequester;
import interfaces.IClientPacket;
import interfaces.IServer;
import manager.CollectionManager;
import manager.CommandManager;
import util.EnvVariablesUtil;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketAddress;

public class Main {
    public static void main(String[] args) {
        InputRequester inputRequester = new InputRequester(new BufferedReader(new InputStreamReader(System.in)));
        Client client = new Client(EnvVariablesUtil.getHost(), EnvVariablesUtil.getPort());
        client.setInputRequester(inputRequester);
        client.registerResponseHandler();
        CommandManager commandManager = new CommandManager(client, inputRequester);

        while (true) {
            System.out.println("Введите команду: ");
            try {
                String command = inputRequester.getReader().readLine().trim().toLowerCase();
                commandManager.findCommand(command);
            } catch (IOException | NullPointerException ignored) {
                LoggerUtil.negative("Данные введены неверно! " + ignored.getMessage());
            }
        }
    }
}
