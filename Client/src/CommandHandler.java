import common.InputRequester;
import interfaces.IClient;

import java.io.IOException;
import java.util.Locale;

public class CommandHandler {
    IClient iClient;
    InputRequester inputRequester;
    public void handleCommand(String rawInput) throws IOException{
        if (rawInput == null){
            System.exit(0);
        }
        String [] split = rawInput.trim().split(" ");
        String commandName = split[0].toLowerCase();
    }
}
