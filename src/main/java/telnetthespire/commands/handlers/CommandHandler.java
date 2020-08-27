package telnetthespire.commands.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.CommandParserRegister;
import telnetthespire.commands.parsers.CommandParser;

public abstract class CommandHandler<T>  implements Command {

    protected static final Logger logger = LogManager.getLogger(CommandHandler.class.getName());
    protected T arguments;
    protected final CommandParser parser;

    public boolean executeIfAvailable() throws InvalidCommandException {
        if (!parser.isAvailable())
            throw invalidCommand(
                "Unavailable command: " + parser.getName() + ". Possible commands: " + CommandParserRegister.getAvailableCommandNames()
            );
        return execute();
    }

    protected CommandHandler(CommandParser parser, T arguments) {
        this.parser = parser;
        this.arguments = arguments;
    }

    protected CommandHandler(CommandParser parser) {
        this.parser = parser;
    }

    public InvalidCommandException invalidUsage (InvalidCommandException.InvalidCommandFormat format) {
        return invalidCommand(format, parser.getUsage().orElse(""));
    }

    public InvalidCommandException invalidUsage (InvalidCommandException.InvalidCommandFormat format, String message) {
        return invalidCommand(format, message + parser.getUsage().orElse(""));
    }

    public InvalidCommandException invalidCommand (String message) {
        return new InvalidCommandException(message);
    }

    public InvalidCommandException invalidCommand (InvalidCommandException.InvalidCommandFormat format, String message) {
        return new InvalidCommandException(new String[] {parser.getName()}, format, message);
    }
}
