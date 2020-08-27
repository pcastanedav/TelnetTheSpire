package telnetthespire.commands.handlers;

import telnetthespire.ChoiceScreenUtils;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.CommandParser;

public class Confirm extends CommandHandler<NoArguments> {

    public Confirm(CommandParser parser) {
        super(parser);
    }

    @Override
    public boolean execute() {
        ChoiceScreenUtils.pressConfirmButton();
        return true;
    }
}
