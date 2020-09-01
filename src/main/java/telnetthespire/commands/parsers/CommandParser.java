package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.Command;
import telnetthespire.commands.Utils;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.ArgumentType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public abstract class CommandParser {

    protected final String _commandName;
    protected String _usage;
    protected final List<String> _argumentNames;
    protected final HashMap<String, EnumSet<ArgumentType>> _signature;
    protected final List<String> _requiredArguments;

    protected CommandParser() {

        List<Argument> arguments = Arrays.stream(this.getClass().getAnnotationsByType(Argument.class))
            .sorted(comparing(Argument::required))
            .collect(toList());

        _argumentNames = arguments.stream().map(Argument::name).distinct().collect(toList());

        // Converts all the arguments specified with the Argument annotation into a map
        _signature =  arguments.stream().collect(toMap(
           Argument::name,
           a -> EnumSet.of(a.type()),
           (a, b) -> { a.addAll(b); return a; },
           HashMap::new ));
        _requiredArguments = arguments.stream().filter(Argument::required).map(Argument::name).collect(Collectors.toList());

        if (this.getClass().isAnnotationPresent(Usage.class)) {
           _usage = this.getClass().getAnnotation(Usage.class).value();
        }
        _commandName = this.getClass().getAnnotation(Name.class).value();
    }

    public <T extends Enum<T>> T getEnumValue (Class<T> enumType, String input) {
        return Utils.enumSortOfMatch(enumType, input).orElseThrow(() ->
            invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, input)
        );
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

    public Integer getMinimumArgumentCount() {
        return _requiredArguments.size();
    }

    public EnumSet<ArgumentType> getArgumentSignature(int index) {
        if (index < 0 || index >= _argumentNames.size())
            return EnumSet.noneOf(ArgumentType.class);
        String name = _argumentNames.get(index);
        return _signature.containsKey(name)
            ? _signature.get(name)
            : EnumSet.noneOf(ArgumentType.class);
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
