package telnetthespire.commands.parsers;


import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.Save;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("save")
public class SaveParser  extends CommandParser {

    @Override
    public boolean isAvailable() {
        return isInDungeon();
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Save(this);
    }
}
