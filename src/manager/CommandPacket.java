package manager;

import Collection.Organization;
import common.Command;
import common.InputRequester;
import common.InputUtil;
import interfaces.IServer;
import interfaces.IServerCommand;
import interfaces.IServerPacket;
import util.LoggerUtil;
import util.SerializationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketAddress;
import java.util.Locale;

public class CommandPacket implements IServerPacket {
    String command;
    Organization organization; // легко
    transient InputRequester inputRequester = new InputRequester(new BufferedReader(new InputStreamReader(System.in)));


    public CommandPacket(String command) {
        this.command = command;
        if(command.equalsIgnoreCase("add") || command.equalsIgnoreCase("add_if_max")){
            organization = InputUtil.requestOrganization(inputRequester);
        }
    }

    public void handleOnServer(IServer server, SocketAddress clientAddress) {
        server.setOrganization(organization);
        IServerCommand command = (IServerCommand) CommandRegister.getCommands()
                .get((this.command.split(" ")[0]));
        server.send(clientAddress, command.execute(server, this.command));

    }

}
