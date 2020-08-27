package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import telnetthespire.GameStateListener;
import telnetthespire.commands.arguments.StartArguments;
import telnetthespire.commands.parsers.StartParser;

public class Start extends CommandHandler<StartArguments> {

    public Start(StartParser startParser, StartArguments arguments) {
        super(startParser, arguments);
    }
    @Override
    public boolean execute() {

        if (arguments.IsTrialSeed) {
            Settings.specialSeed = arguments.Seed;
            Settings.isTrial = true;
        }
        Settings.seed = arguments.Seed;
        Settings.seedSet = arguments.SeedSet;
        AbstractDungeon.generateSeeds();

        AbstractDungeon.ascensionLevel = arguments.AscensionLevel;
        AbstractDungeon.isAscensionMode = arguments.AscensionLevel > 0;
        CardCrawlGame.startOver = true;
        CardCrawlGame.mainMenuScreen.isFadingOut = true;
        CardCrawlGame.mainMenuScreen.fadeOutMusic();
        CharacterManager manager = new CharacterManager();
        manager.setChosenCharacter(arguments.SelectedClass);
        CardCrawlGame.chosenCharacter = arguments.SelectedClass;
        GameStateListener.resetStateVariables();

        return true;
    }
}
