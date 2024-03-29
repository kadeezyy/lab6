package util;

public class EnvVariablesUtil {
    public static int getPort(){
        int port = 2222;
        try{
            port = Integer.parseInt(System.getenv("PORT"));
        }catch(Exception ex){
            LoggerUtil.negative("Не удалось получить порт из переменной среды ");
            LoggerUtil.infoAsString(String.format("Используется стандартный порт \"%d\"", port));
        }
        return port;
    }

    public static String getHost(){
        String host = System.getenv("HOST");
        if (host == null){
            host = "localhost";
            LoggerUtil.negative("Не удалось получить имя хоста из переменной среды ");
            LoggerUtil.infoAsString(String.format("Используется локальный хост \"%s\"",host));
        }
        return host;
    }
}
