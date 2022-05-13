package common;

import util.LoggerUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;

public class InputRequester {
    BufferedReader reader;

    public InputRequester(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Requests input by calling Reader.readline() and handles it with provided function
     *
     * @param message Message that will be printed to user when input request is sent
     * @param handler Function that will handle input
     * @param <T>     Required object
     * @return Handled input
     */
    public <T> T requestInput(String message, Function<String, Object> handler) {
        T value = null;
        while (value == null) {
            System.out.printf("%s", message);
            boolean error = false;

            try {
                value = (T) handler.apply(reader.readLine().trim());
            } catch (IOException ex) {
                error = true;
            }


            if (error || value == null) {
                LoggerUtil.negative("Формат данных не соответствует требуемым");
            } else {
                System.out.println(value.equals("null") ? "Значение не определено" : value);
            }
        }
        return value;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader){
        this.reader = reader;
    }
}
