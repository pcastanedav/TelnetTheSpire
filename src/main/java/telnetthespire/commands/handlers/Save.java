package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import telnetthespire.commands.parsers.CommandParser;
import telnetthespire.commands.arguments.NoArguments;

public class Save extends CommandHandler<NoArguments> {

    public Save(CommandParser parser) {
        super(parser);
    }

    @Override
    public boolean execute() {
        CardCrawlGame.music.fadeAll();
        AbstractDungeon.getCurrRoom().clearEvent();
        AbstractDungeon.closeCurrentScreen();
        CardCrawlGame.startOver();
        if (RestRoom.lastFireSoundId != 0L)
            CardCrawlGame.sound.fadeOut("REST_FIRE_WET", RestRoom.lastFireSoundId);
        if (!AbstractDungeon.player.stance.ID.equals("Neutral") && AbstractDungeon.player.stance != null)
            AbstractDungeon.player.stance.stopIdleSfx();
        return false;
    }
}
