package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.ChoiceScreenUtils;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.Cancel;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("cancel")
@Alias("skip")
@Alias("return")
@Alias("leave")
public class CancelParser extends CommandParser {

    @Override
    public String getName() {
        return ChoiceScreenUtils.getCancelButtonText();
    }

    @Override
    public boolean isAvailable() {
        return isInDungeon()
                && ChoiceScreenUtils.isCancelButtonAvailable();
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Cancel(this);
    }

}
