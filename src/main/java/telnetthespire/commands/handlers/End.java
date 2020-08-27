package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.CommandParser;

public class End extends CommandHandler<NoArguments> {

    public End(CommandParser parser) {
        super(parser);
    }

    @Override
    public boolean execute() {

        AbstractDungeon.overlayMenu.endTurnButton.disable(true);

        return true;
    }
}
