package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Executor;

import static telnetthespire.commands.Utils.isInDungeon;

/*
public class Play implements Command {

    static {
        Executor.registerHandler(new Play());
    }

    @Override
    public String[] getNames() {
        return new String[] {"play", "p"};
    }

    @Override
    public boolean isAvailable() {
        if(!isInDungeon()) return false;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.isScreenUp) return false;
        // TODO: this does not check the case where there is no legal target for a target card.
        // Play command is not available if none of the cards are playable.
        return AbstractDungeon.player.hand.group.stream().anyMatch(card -> card.canUse(AbstractDungeon.player, null));
    }

    @Override
    public void execute(String[] tokens) throws InvalidCommandException {

    }
}
*/