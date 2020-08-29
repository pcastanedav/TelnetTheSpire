package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import telnetthespire.GameStateListener;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.arguments.PotionArguments;
import telnetthespire.commands.parsers.CommandParser;

public class Potion extends CommandHandler<PotionArguments> {

    public Potion(CommandParser parser, PotionArguments arguments) {
        super(parser, arguments);
    }

    @Override
    public boolean execute() throws InvalidCommandException {
        if (arguments.Target < 0 || arguments.Target >= AbstractDungeon.player.potionSlots)
            throw invalidCommand("Potion index out of bounds.");

        AbstractPotion selectedPotion = AbstractDungeon.player.potions.get(arguments.Target);
        if (selectedPotion instanceof PotionSlot)
            throw invalidCommand("No potion in the selected slot.");

        if (arguments.Action == PotionArguments.Type.USE && !selectedPotion.canUse())
            throw invalidCommand("Selected potion cannot be used.");

        if (arguments.Action == PotionArguments.Type.DISCARD && !selectedPotion.canDiscard())
            throw invalidCommand("Selected potion cannot be discarded.");

        if (arguments.Action == PotionArguments.Type.USE) {
            if (selectedPotion.targetRequired) throwPotionAtStuff(selectedPotion, arguments.Monster);
            else selectedPotion.use(AbstractDungeon.player);
            AbstractDungeon.player.relics.forEach(AbstractRelic::onUsePotion);
        }
        AbstractDungeon.topPanel.destroyPotion(selectedPotion.slot);
        GameStateListener.registerStateChange();
        return true;
    }

    private void throwPotionAtStuff (AbstractPotion potion, Integer monsterIndex) throws InvalidCommandException {

        if (monsterIndex >= AbstractDungeon.getCurrRoom().monsters.monsters.size())
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, Integer.toString(monsterIndex));

        AbstractMonster monster = monsterIndex < 0
            ? AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(m -> m.currentHealth > 0).findFirst().orElseThrow(() ->
                invalidCommand(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT, " Selected potion requires a target.")
            )
            : AbstractDungeon.getCurrRoom().monsters.monsters.get(monsterIndex);

        potion.use(monster);
    }
}
