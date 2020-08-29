package telnetthespire.commands.handlers;

import telnetthespire.ChoiceScreenUtils;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.CommandParser;

public class Cancel extends CommandHandler<NoArguments> {

    public Cancel(CommandParser parser) {
        super(parser);
    }

    @Override
    public boolean execute() {
        ChoiceScreenUtils.pressCancelButton();
        return true;
    }
}
