package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.arguments.PlayArguments;
import telnetthespire.commands.handlers.Play;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("play")
@Alias("p")
@Argument(name="Card", type=ArgumentType.NATURAL, required = true)
@Argument(name="Target", type=ArgumentType.NATURAL)
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
        return new Play(this, new PlayArguments() {{
            Card = getCard(arguments);
            Target = getTarget(arguments);
        }});
    }

    private AbstractCard getCard(Vector<Object> arguments) {

        int card = (Integer) arguments.get(0);
        if(card == 0) card = 10;
        if ((card < 1) || (card > AbstractDungeon.player.hand.size()))
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, String.valueOf(card));

        // If we get the card during parsing we solve the reindex issue
        return AbstractDungeon.player.hand.group.get(card - 1);
    }

    private Integer getTarget(Vector<Object> arguments) {
        if (arguments.size() < 2) return -1;
        return ((Integer) arguments.get(1)) - 1;
    }
}
