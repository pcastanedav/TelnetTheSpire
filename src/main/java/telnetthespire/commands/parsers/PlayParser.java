package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Alias;
import telnetthespire.commands.annotations.Name;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("play")
@Alias("p")
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
        return null;
    }
}
