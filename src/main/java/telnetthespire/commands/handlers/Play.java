package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.arguments.PlayArguments;
import telnetthespire.commands.parsers.CommandParser;

import java.util.Optional;

public class Play  extends CommandHandler<PlayArguments> {

    public Play(CommandParser parser, PlayArguments arguments) {
        super(parser, arguments);
    }

    @Override
    public boolean execute() throws InvalidCommandException {

        Optional<AbstractMonster> target = findTargetMonster();

        if(!(arguments.Card.canUse(AbstractDungeon.player, target.orElse(null))))
            throw invalidCommand("Selected card cannot be played with the selected target.");

        boolean requireTarget = arguments.Card.target == AbstractCard.CardTarget.ENEMY || arguments.Card.target == AbstractCard.CardTarget.SELF_AND_ENEMY;

        if (requireTarget) {
            AbstractMonster monster = target.orElseThrow(() -> invalidCommand("Selected card requires an enemy target."));
            if (AbstractDungeon.player.hasPower("Surrounded"))
                AbstractDungeon.player.flipHorizontal = (monster.drawX < AbstractDungeon.player.drawX);
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(arguments.Card, monster));
        } else {
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(arguments.Card, null));
        }

        return true;
    }

    private Optional<AbstractMonster> findTargetMonster() throws InvalidCommandException {
        if (arguments.Target == -1) return findAliveMonster();
        if (arguments.Target < 0 || arguments.Target >= AbstractDungeon.getCurrRoom().monsters.monsters.size())
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, arguments.Target.toString());
        return Optional.of(AbstractDungeon.getCurrRoom().monsters.monsters.get(arguments.Target));
    }

    private Optional<AbstractMonster> findAliveMonster () {
        return AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(m -> m.currentHealth > 0).findFirst();
    }

}
