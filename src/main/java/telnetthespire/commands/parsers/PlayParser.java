package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.PlayArguments;
import telnetthespire.commands.handlers.Play;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("play")
@Alias("p")
@Usage("\nUsage: play card_index [TargetIndex]")
public class PlayParser extends CommandParser {

    @Override
    public boolean isAvailable() {
        if(!isInDungeon()) return false;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.isScreenUp) return false;
        // TODO: this does not check the case where there is no legal target for a target card.
        // Play command is not available if none of the cards are playable.
        return AbstractDungeon.player.hand.group.stream().anyMatch(card -> card.canUse(AbstractDungeon.player, null));
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {

        PlayArguments playArguments = new PlayArguments();

        playArguments.Card = getCard(arguments);
        playArguments.Target = getTargetIndex(arguments);

        return new Play(this, playArguments);
    }

    private AbstractCard getCard(Vector<Object> arguments) {

        if (arguments.size() < 1)
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT);

        Object raw = arguments.get(0);

        if (!(raw instanceof Integer))
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, raw.toString());

        int card = (Integer) raw;
        if(card == 0) card = 10;
        if ((card < 1) || (card > AbstractDungeon.player.hand.size()))
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, raw.toString());

        // If we get the card during parsing we might solve the reindex issue
        return AbstractDungeon.player.hand.group.get(card - 1);
    }

    private Integer getTargetIndex(Vector<Object> arguments) {
        if (arguments.size() < 2) return -1;

        Object raw = arguments.get(1);

        if (!(raw instanceof Integer))
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, raw.toString());

        int target = ((Integer) raw) - 1;
        return target;
    }
}
