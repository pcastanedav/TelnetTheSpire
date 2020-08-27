package telnetthespire.commands.parsers;


import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.End;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("end")
public class EndParser extends CommandParser {

    @Override
    public boolean isAvailable() {
        return isInDungeon() && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp;
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new End(this);
    }
}
