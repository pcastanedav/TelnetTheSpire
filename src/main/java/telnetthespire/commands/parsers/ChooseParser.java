package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.ChoiceScreenUtils;
import telnetthespire.commands.Command;
import telnetthespire.commands.CommandParserRegister;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.arguments.ChooseArguments;
import telnetthespire.commands.handlers.Choose;

import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("choose")
@Alias("c")
@Argument(name="Choice", type=ArgumentType.NATURAL, required=true)
@Argument(name="Choice", type=ArgumentType.TEXT)
public class ChooseParser extends CommandParser {

    @Override
    public boolean isAvailable() {
        return CommandParserRegister
            .getParser("play")
            .flatMap(playParser -> Optional.of( isInDungeon() && !playParser.isAvailable() && !ChoiceScreenUtils.getCurrentChoiceList().isEmpty()))
            .orElse(false);
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {

        ChooseArguments chooseArguments = new ChooseArguments();

        chooseArguments.Choice = arguments.stream().map(Object::toString).collect(Collectors.joining(" "));
        Object raw = arguments.get(0);
        if (Integer.class.isInstance(raw))
            chooseArguments.Index = ((Integer) raw) - 1;

        return new Choose(this, chooseArguments);
    }
}
