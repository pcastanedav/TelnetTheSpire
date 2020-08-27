package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.ChoiceScreenUtils;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.Confirm;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("confirm")
@Alias("proceed")
public class ConfirmParser extends CommandParser {

    @Override
    public String getName() {
        return ChoiceScreenUtils.getConfirmButtonText();
    }

    @Override
    public boolean isAvailable() {
        return isInDungeon()
                && ChoiceScreenUtils.isConfirmButtonAvailable();
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Confirm(this);
    }

}
