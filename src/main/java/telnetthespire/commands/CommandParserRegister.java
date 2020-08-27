package telnetthespire.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telnetthespire.commands.parsers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandParserRegister {

    protected static final Logger logger = LogManager.getLogger(CommandParserRegister.class.getName());
    private static final HashMap<String, CommandParser> _parsers = new HashMap<>();
    private static final HashMap<String, String> _aliases = new HashMap<>();

    static {

        registerParser(new AbandonParser());
        registerParser(new ChooseParser());
        registerParser(new ContinueParser());
        registerParser(new SaveParser());
        registerParser(new StartParser());
        registerParser(new StateParser());
        registerParser(new PlayParser());
        registerParser(new KeyParser());

        getParsers().map(CommandParser::getName).forEach(name -> {
            for (int i = 1; i <= name.length() - 1; i++) {
                String alias = name.substring(0, i);
                if (_aliases.putIfAbsent(alias, name) == null) {
                    logger.info("Created implicit alias " + alias + " for command: " + name);
                }
            }
        });

    }

    private static void registerParser(CommandParser parser) {
        String name = parser.getName();
        _parsers.put(name, parser);
        logger.info("Registered parser for command: " + name);
        parser.getAliases().forEach(alias -> {
            if (_aliases.putIfAbsent(alias, name) == null)
            logger.info("Created explicit alias " + alias + " for command: " + name);
        });
    }

    public static Optional<CommandParser> getParser(String alias) {
       String name = _aliases.getOrDefault(alias, alias);
       return _parsers.containsKey(name)
           ? Optional.of(_parsers.get(name))
           : Optional.empty();
    }

    public static ArrayList<String> getAvailableCommandNames() {
        return getAvailableParsers()
            .map(CommandParser::getName)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Stream<CommandParser> getAvailableParsers() {
        return getParsers().filter(CommandParser::isAvailable);
    }

    public static Stream<CommandParser> getParsers() {
        return _parsers.values().stream();
    }

}
