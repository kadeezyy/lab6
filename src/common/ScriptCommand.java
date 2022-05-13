package common;

import interfaces.IServer;
import manager.CollectionManager;

import java.io.BufferedReader;

public abstract class ScriptCommand {
    public abstract void execute(IServer server, String command, BufferedReader reader);
    public abstract String getName();
}
