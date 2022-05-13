package util;

public class LoggerUtil {
    /**
     * Prints message on behalf of the CCL (Console Collection Lab)
     *
     * @param message Message that will be printed
     */

    public static void log(String message) {
        System.out.printf("%s%s%n", message, ConsoleColor.RESET);
    }

    public static void negative(String message) {
        log(ConsoleColor.RED + message);
    }

    public static String positiveAsString(String message) {
        return String.format("%s%s",message, ConsoleColor.GREEN);
    }

    public static void infoAsString(String message) {
        log(ConsoleColor.YELLOW + message);
    }

    public static String info(String message) {
        return logAsString(ConsoleColor.YELLOW + message);
    }

    public static String negativeAsString(String message){
        return logAsString(ConsoleColor.RED + message);
    }

    public static String logAsString(String message){
        return String.format("%s%s", message, ConsoleColor.RESET);
    }

    public static void positive(String message) {
        log(ConsoleColor.GREEN + message);
    }


    /**
     * Colors for colorizing messages
     * Uses ANSI escape codes
     */
    public interface ConsoleColor {
        String RESET = "\u001B[0m",
                RED = "\u001b[31m",
                GREEN = "\u001b[32m",
                YELLOW = "\u001b[33m",
                BLUE = "\u001b[34m",
                MAGENTA = "\u001b[35m",
                CYAN = "\u001b[36m",
                WHITE = "\u001b[37m";
    }
}
