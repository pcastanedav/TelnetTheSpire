package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import telnetthespire.ChoiceScreenUtils;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.arguments.ChooseArguments;
import telnetthespire.commands.parsers.ChooseParser;

import java.util.ArrayList;

public class Choose extends CommandHandler<ChooseArguments> {

    public Choose(ChooseParser parser, ChooseArguments arguments) {
        super(parser, arguments);
    }

    @Override
    public boolean execute() throws InvalidCommandException {
        ChoiceScreenUtils.executeChoice(getValidChoiceIndex());
        return true;
    }

    private int getValidChoiceIndex() throws InvalidCommandException {

        ArrayList<String> validChoices = ChoiceScreenUtils.getCurrentChoiceList();

        if(validChoices.size() == 0)
            throw invalidCommand("The choice command is not implemented on this screen.");

        int index = validChoices.indexOf(arguments.Choice);
        if (index < 0 && arguments.Index >= 0) index = arguments.Index;

        if (index < 0 || index >= validChoices.size())
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, arguments.Choice);

        if (validChoices.get(index).contains("add potion") && AbstractDungeon.player.potions.stream().noneMatch(potion -> potion instanceof PotionSlot))
            throw invalidCommand("You first need to discard a potion.");

        return index;
    }
}
