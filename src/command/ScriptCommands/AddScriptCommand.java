package command.ScriptCommands;

import Collection.Organization;
import common.ScriptCommand;
import interfaces.IServer;
import manager.CollectionManager;
import sun.rmi.runtime.Log;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.time.LocalDateTime;

public class AddScriptCommand extends ScriptCommand {
    Organization.Coordinates newCoordinates;
    Organization.OrganizationType orgType;
    CollectionManager collectionManager;

    public void execute(IServer server, String command, BufferedReader reader){
        collectionManager = server.getCollectionManager();
        Organization organization = executeMain(reader);
        boolean isSuccessfullyAdded = collectionManager.getCollection().add(organization);
        if(isSuccessfullyAdded){
            LoggerUtil.positive("Элемент успешно добавлен в коллекцию");
        }else{
            LoggerUtil.negative("Элемент не удалось добавить в коллекцию");
        }
    }
    public String getName(){
        return "add";
    }
    public Organization executeMain(BufferedReader reader){
        try {
            String name = reader.readLine().trim();
            int x = Integer.parseInt(reader.readLine().trim());
            int y = Integer.parseInt(reader.readLine().trim());
            newCoordinates = new Organization.Coordinates(x, y);
            Float annualTurnover = Float.parseFloat(reader.readLine().trim());
            String fullName = reader.readLine().trim();
            int employeesCount = Integer.parseInt(reader.readLine());
            orgType = Organization.OrganizationType.
                    valueOf(reader.readLine().trim());
            String postalAddress = reader.readLine().trim();
            Organization.Address zipCode = new Organization.Address(postalAddress);
            Organization organization = new Organization(name, newCoordinates, annualTurnover,
                    fullName, employeesCount, orgType, zipCode);
            organization.setCreationDate(LocalDateTime.now().toLocalDate());
            return organization;
        } catch (Exception ex) {
            LoggerUtil.negative("Неверно введены данные в скрипте");
        }
        return null;
    }
}
