package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import telnetthespire.InvalidCommandException;

import static telnetthespire.commands.Utils.isInDungeon;

/*
public class Potion implements Command {

    @Override
    public String[] getNames() {
        return new String[] {"potion"};
    }

    @Override
    public boolean isAvailable() {
        return isInDungeon()
            && AbstractDungeon.player.potions.stream().anyMatch(potion -> !(potion instanceof PotionSlot));
    }

    @Override
    public void execute(String[] tokens) throws InvalidCommandException {

    }
}
*/