package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.Utils;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.arguments.KeyArguments;
import telnetthespire.commands.handlers.Key;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("key")
@Argument(name="KeyCode", required=true)
@Argument(name="Timeout", type=ArgumentType.NATURAL)
public class KeyParser extends CommandParser {

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Key(this, new KeyArguments() {{
            KeyCode = getKeyCode(arguments);
            Timeout = getTimeout(arguments);
        }});
    }

    @Override
    public boolean isAvailable() {
        return isInDungeon();
    }

    private Integer getKeyCode (Vector<Object> arguments) throws ParseCancellationException {
        String keyCodeString = arguments.get(0).toString().toUpperCase();
        return Utils.getKeyCode(keyCodeString).orElseThrow(() ->
            invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, keyCodeString)
        );
    }

    private Integer getTimeout (Vector<Object> arguments) {
       if (arguments.size() < 2) return 100;
       int timeout = (int) arguments.get(1);
       if(timeout < 0)
           throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, String.valueOf(timeout));
       return timeout;
    }
}
