package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.lang3.tuple.Pair;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.ArgumentType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CommandParser {

    protected final String _commandName;
    protected String _usage;
    protected final Vector<Pair<ArgumentType, Boolean>> _signature = new Vector<>();

    protected CommandParser() {
        if (this.getClass().isAnnotationPresent(Argument.class)) {
            _signature.addAll(
               Arrays.stream(this.getClass().getAnnotationsByType(Argument.class))
               .map(a -> Pair.of(a.value(), a.required()))
               .sorted(Comparator.comparing(Pair::getValue))
               .collect(Collectors.toList())
            );
        }
        if (this.getClass().isAnnotationPresent(Usage.class)) {
           _usage = this.getClass().getAnnotation(Usage.class).value();
        }
        _commandName = this.getClass().getAnnotation(Name.class).value();
    }

    public Stream<String> getAliases() {
        return Arrays.stream(this.getClass().getAnnotationsByType(Alias.class)).map(Alias::value);
    }

    public String getAnnotatedName() {
        return _commandName;
    }

    public String getName() {
        return getAnnotatedName();
    }

    public boolean isAvailable () {
        return true;
    }

    public abstract Command parse(Vector<Object> arguments) throws ParseCancellationException;

    public Optional<String> getUsage() {
        return Optional.ofNullable(_usage);
    }

    public int getMinimum
    public Optional<Pair<ArgumentType,Boolean>> getSignature(int index) {
        return index >= _signature.size()
            ? Optional.empty()
            : Optional.of(_signature.get(index));
    }

    public ParseCancellationException invalidUsage (InvalidCommandException.InvalidCommandFormat format) {
        return invalidCommand(format, getUsage().orElse(""));
    }

    public ParseCancellationException invalidUsage (InvalidCommandException.InvalidCommandFormat format, String message) {
        return invalidCommand(format, message + getUsage().orElse(""));
    }

    public ParseCancellationException invalidCommand (InvalidCommandException.InvalidCommandFormat format, String message) {
        return new ParseCancellationException(new InvalidCommandException(new String[] {_commandName}, format, message));
    }
}
