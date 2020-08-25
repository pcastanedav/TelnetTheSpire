package telnetthespire.commands;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Aliases;
import telnetthespire.commands.annotations.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class Command {

    protected static final Logger logger = LogManager.getLogger(Command.class.getName());

    protected CommandArguments _arguments;

    private String _name;

    public String getName() {
        if (_name == null) _name = this.getClass().getAnnotation(Name.class).value();
        return _name;
    }

    private ArrayList<String> _aliases;
    public Optional<ArrayList<String>> getAliases() {
        if (_aliases == null) {
            Aliases aliases = this.getClass().getAnnotation(Aliases.class);
            if (aliases != null)
            _aliases = Arrays.stream(aliases.value()).map(Alias::value).collect(Collectors.toCollection(ArrayList::new));
        }
        return Optional.ofNullable(_aliases);
    }

    public boolean isAvailable () {
        return true;
    }

    public abstract boolean execute(Optional<Vector<Object>> parser) throws InvalidCommandException;
}
