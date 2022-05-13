package manager;

import interfaces.IClient;
import interfaces.IClientPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagePacket implements IClientPacket {
    private List<String> lines;

    public MessagePacket(String... lines){
        this.lines = new ArrayList<>(Arrays.asList(lines));
    }

    @Override
    public void handleOnClient(IClient client) {
        lines.forEach(System.out::println);
    }

    public void addLines(String... lines){
        this.lines.addAll(Arrays.asList(lines));
    }

    public List<String> getLines() {
        return lines;
    }
    public void addLines(List<String> lines){
        this.lines.addAll(lines);
    }
}
