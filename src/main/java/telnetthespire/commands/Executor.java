package telnetthespire.commands;

import org.apache.commons.lang3.tuple.Pair;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.handlers.Start;
import telnetthespire.commands.handlers.State;
import telnetthespire.commands.parsers.JargonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public class Executor {

    private static final HashMap<String, Command> _handlers = new HashMap<>();

    static {
       registerHandler(new Start());
       registerHandler(new State());
    }

    public static Command getHandler(String name) {
        return _handlers.get(name);
    }

    public static void registerHandler(Command handler) {
        String name = handler.getName();
        _handlers.put(name, handler);
    }

    public static boolean execute (String input) throws InvalidCommandException {

        JargonParser parser = new JargonParser(input.startsWith(".") ? input : "." + input);
        HashMap<String, String> names = getAvailableNames();
        boolean stateChanged = false;

        for (Pair<String,Optional<Vector<Object>>> parsedCommand: parser.getListener().getOrder()) {
            if (names.containsKey(parsedCommand.getKey())) {
                stateChanged = stateChanged || getHandler(names.get(parsedCommand.getKey())).execute(parsedCommand.getValue());
            } else {
                throw new InvalidCommandException("Invalid command: " + parsedCommand.getKey() + ". Possible commands: " + getAvailableCommands());
            }
        }
        return stateChanged;
    }

    public static HashMap<String, String> getAvailableNames() {
        HashMap<String, String> aliases = new HashMap<>();
        getAvailableCommands().forEach(name -> {
            for (int i = 1; i <= name.length(); i++)
                aliases.putIfAbsent(name.substring(0, i), name);
        });
        return aliases;
    }

    public static ArrayList<String> getAvailableCommands() {
        return _handlers
                .values()
                .stream()
                .filter(Command::isAvailable)
                .map(Command::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
