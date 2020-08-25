package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import telnetthespire.ChoiceScreenUtils;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.*;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;

import java.util.ArrayList;
import java.util.Optional;

import static telnetthespire.commands.Utils.isInDungeon;
/*
@Name("choose")
@Alias("c")
class Choose extends Command {

    @Override
    public boolean isAvailable() {
        Optional<Command> playCommand = Executor.getHandler("play");
        if (playCommand.isPresent())
            return isInDungeon()
                && !playCommand.get().isAvailable()
                && !ChoiceScreenUtils.getCurrentChoiceList().isEmpty();
        return false;
    }

    @Override
    public boolean execute(Parameters parameters) throws InvalidCommandException {

        if (parameters == null)
            throw new InvalidCommandException(new String[]{getName()}, InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT, " A choice is required.");

        int choice_index = getValidChoiceIndex(parameters);

        ChoiceScreenUtils.executeChoice(choice_index);

        return true;
    }

    private int getValidChoiceIndex(Parameters parameter) throws InvalidCommandException {

        ArrayList<String> validChoices = ChoiceScreenUtils.getCurrentChoiceList();

        String choice = parameter.toString();

        int choice_index = validChoices.contains(choice)
                ? validChoices.indexOf(choice)
                : parameter.toIndex();

        if (choice_index < 0 || choice_index >= validChoices.size())
            throw new InvalidCommandException(new String[]{getName()}, InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, choice);

        if (validChoices.get(choice_index).contains("add potion") && AbstractDungeon.player.potions.stream().noneMatch(potion -> potion instanceof PotionSlot))
            throw new InvalidCommandException("You first need to discard a potion.");

        return choice_index;
    }

}

 */