package telnetthespire.commands;

import telnetthespire.InvalidCommandException;
import telnetthespire.commands.annotations.Usage;

import java.util.Optional;
import java.util.Vector;

public abstract class CommandArguments {

    protected final String commandName;
    protected final Vector<Object> arguments;
    protected final String _usage;

    protected CommandArguments(String commandName, Vector<Object> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
        _usage = this.getClass().getAnnotation(Usage.class).value();
    }

    public Optional<String> getUsage() {
        return Optional.ofNullable(_usage);
    }

    public InvalidCommandException invalidUsage (InvalidCommandException.InvalidCommandFormat format) {
        return invalidCommand(format, getUsage().orElse(""));
    }

    public InvalidCommandException invalidUsage (InvalidCommandException.InvalidCommandFormat format, String message) {
        return invalidCommand(format, message + getUsage().orElse(""));
    }

    public InvalidCommandException invalidCommand (InvalidCommandException.InvalidCommandFormat format, String message) {
        return new InvalidCommandException(new String[] {commandName}, format, message);
    }
}
