package manager;

import Collection.Organization;
import command.*;
import command.ScriptCommands.AddIfMaxScriptCommand;
import command.ScriptCommands.AddScriptCommand;
import command.ScriptCommands.RemoveLowerScriptCommand;
import command.ScriptCommands.UpdateByIdScriptCommand;
import common.Command;
import common.ScriptCommand;
import org.json.simple.*;
import Collection.OrganizationCollection;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.LoggerUtil;

import java.io.*;
import java.util.*;


public class CollectionManager {
    OrganizationCollection collection = new OrganizationCollection();
    long initDate = 0;
    BufferedReader reader;
    String fileName = String.valueOf(System.getenv("FILE_PATH"));

    public CollectionManager() {
    }

    List<String> commandHistory = new ArrayList<>();
    Set<String> scriptInProcess = new HashSet<>();


    /**
     * writes data from collection into json file
     */
    public MessagePacket writeCollectionToFile(String fileName) {
        boolean isSuccessfully = false;
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(fileName))) {
            JSONObject jo = new JSONObject();
            Map map = new LinkedHashMap<>();
            for (Organization organization : collection) {
                map.put("id", organization.getId());
                map.put("name", organization.getName());
                map.put("coordinates", organization.getCoordinates());
                map.put("annualTurnover", organization.getAnnualTurnover());
                map.put("fullName", organization.getFullName());
                map.put("employeesCount", organization.getEmployeesCount());
                map.put("type", organization.getType());
                map.put("postalAddress", organization.getPostalAddress());
                jo.put(organization.getId(), map);
                os.write(jo.toJSONString().getBytes());
                map.clear();
                isSuccessfully = true;
            }
            if (isSuccessfully) {
                return new MessagePacket(LoggerUtil.positiveAsString("Коллекция была успешно сохранена в файл"));
            } else {
                return new MessagePacket(LoggerUtil.negativeAsString("Не удалось сохранить коллекцию в файл"));
            }
        } catch (Exception ex) {
            return new MessagePacket(LoggerUtil.negativeAsString("Не удалось сохранить коллекцию в файл " + ex.getMessage()));
        }
    }

    /**
     * Reads collection from a file in json format
     */
    public void readCollectionFromFile() {

        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileName))) {
            Object obj = new JSONParser().parse(fileName);
            JSONObject jo = (JSONObject) obj;
            Map id = (Map) jo.get("ID");

            Iterator<Map.Entry> itr = id.entrySet().iterator();
            List<String> keyList = new ArrayList<>();
            List<String> valueList = new ArrayList<>();
            while (itr.hasNext()) {
                Map.Entry pair = itr.next();
                keyList.add((String) pair.getKey());
                valueList.add((String) pair.getValue());
            }
            String[] coordinates = valueList.get(1).replaceFirst(".+=;", "").split(";");
            Organization organization = new Organization(valueList.get(0),
                    new Organization.Coordinates(Integer.parseInt(coordinates[0]),
                            Integer.parseInt(coordinates[1])),
                    Float.parseFloat(valueList.get(2)), valueList.get(3),
                    Integer.parseInt(valueList.get(4)),
                    Organization.OrganizationType.valueOf(valueList.get(5)),
                    new Organization.Address(valueList.get(6)));
            collection.add(organization);
            initDate = System.currentTimeMillis();
            LoggerUtil.positive("Коллекция была успешно загружена из файла" + fileName);
        } catch (FileNotFoundException ex) {
            LoggerUtil.negative("Не удалось сохранить коллекцию из файла" + ex.getMessage());
        } catch (IOException | ParseException ex) {
            LoggerUtil.negative("Не удалось сохранить коллекцию из файла"  + ex.getMessage());
//            ex.printStackTrace();
        }
    }

    public OrganizationCollection getCollection() {
        return collection;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        try {
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException ex) {
            LoggerUtil.negative("Не удалось прочитать скрипт");
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public Set<String> getScriptInProcess() {
        return scriptInProcess;
    }

}
