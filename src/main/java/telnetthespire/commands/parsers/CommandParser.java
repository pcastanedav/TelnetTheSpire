package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;

import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

public abstract class CommandParser {

    protected final String _commandName;
    protected String _usage;

    protected CommandParser() {
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
