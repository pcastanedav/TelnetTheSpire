package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.helpers.input.InputHelper;
import telnetthespire.GameStateListener;
import telnetthespire.commands.arguments.KeyArguments;
import telnetthespire.commands.parsers.KeyParser;
import telnetthespire.patches.InputActionPatch;

public class Key extends CommandHandler<KeyArguments> {

    public Key(KeyParser parser, KeyArguments arguments) {
        super(parser, arguments);
    }
    @Override
    public boolean execute() {

        InputActionPatch.doKeypress = true;
        InputActionPatch.key = arguments.KeyCode;
        InputHelper.updateFirst();
        GameStateListener.setTimeout(arguments.Timeout);
        return true;
    }
}
