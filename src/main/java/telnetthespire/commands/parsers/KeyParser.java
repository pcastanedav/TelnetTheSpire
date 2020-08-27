package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.Utils;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.arguments.KeyArguments;
import telnetthespire.commands.handlers.Key;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("key")
public class KeyParser extends CommandParser {

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        KeyArguments keyArguments = new KeyArguments();
        keyArguments.KeyCode = getKeyCode(arguments);
        keyArguments.Timeout = getTimeout(arguments);
        return new Key(this, keyArguments);
    }

    @Override
    public boolean isAvailable() {
        return isInDungeon();
    }

    private Integer getKeyCode (Vector<Object> arguments) throws ParseCancellationException {
        if (arguments.size() < 1) {
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT, "");
        }
        String keyCodeString = arguments.get(0).toString().toUpperCase();
        return Utils.getKeyCode(keyCodeString).orElseThrow(() ->
            invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, keyCodeString)
        );
    }

    private Integer getTimeout (Vector<Object> arguments) {
       if (arguments.size() < 2) return 100;
       Object raw = arguments.get(2);
       if (!(raw instanceof Integer))
           throw invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, raw.toString());
       int timeout = (int) raw;
       if(timeout < 0)
           throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, raw.toString());
       return timeout;
    }
}
