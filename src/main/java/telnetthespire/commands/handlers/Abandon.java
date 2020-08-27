package telnetthespire.commands.handlers;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import telnetthespire.TelnetTheSpire;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.AbandonParser;

public class Abandon extends CommandHandler<NoArguments> {

    public Abandon(AbandonParser abandonParser) {
        super(abandonParser);
    }

    @Override
    public boolean execute() {

        CardCrawlGame.chosenCharacter = (CardCrawlGame.characterManager.loadChosenCharacter()).chosenClass;

        AbstractPlayer player = AbstractDungeon.player;
        AbstractPlayer.PlayerClass pClass = player.chosenClass;

        logger.info("Abandoning run with " + pClass.name());

        SaveFile file = SaveAndContinue.loadSaveFile(pClass);

        if (Settings.isStandardRun()) {
            CardCrawlGame.playerPref.putInteger(pClass.name() + "_SPIRITS", file.floor_num >= 16 ? 1 : 0);
            CardCrawlGame.playerPref.flush();
        }

        SaveAndContinue.deleteSave(player);

        if (!file.is_ascension_mode)
            StatsScreen.incrementDeath(player.getCharStat());

        CardCrawlGame.mainMenuScreen.abandonedRun = true;
        CardCrawlGame.mainMenuScreen.isSettingsUp = true;

        TelnetTheSpire.mustSendGameState = true;
        return false;
    }

}