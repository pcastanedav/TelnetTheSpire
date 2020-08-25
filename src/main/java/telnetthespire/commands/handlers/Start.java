package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.commons.lang3.tuple.Pair;
import telnetthespire.GameStateListener;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.parsers.StartArguments;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("start")
public class Start extends Command {

    public Start () {}

    @Override
    public boolean isAvailable() {
        return !isInDungeon()
            && !CardCrawlGame.characterManager.anySaveFileExists();
    }

    @Override
    public boolean execute(Optional<Vector<Object>> parsedArguments) throws InvalidCommandException {

        StartArguments arguments = new StartArguments(getName(), parsedArguments.orElse(new Vector<>()));

        AbstractPlayer.PlayerClass selectedClass = arguments.getSelectedClass();
        Integer ascensionLevel = arguments.getAscensionLevel();
        Pair<Boolean, Long> seed = arguments.getSeed();

        Settings.seed = seed.getValue();
        Settings.seedSet = seed.getKey();
        AbstractDungeon.generateSeeds();
        AbstractDungeon.ascensionLevel = ascensionLevel;
        AbstractDungeon.isAscensionMode = ascensionLevel > 0;
        CardCrawlGame.startOver = true;
        CardCrawlGame.mainMenuScreen.isFadingOut = true;
        CardCrawlGame.mainMenuScreen.fadeOutMusic();
        CharacterManager manager = new CharacterManager();
        manager.setChosenCharacter(selectedClass);
        CardCrawlGame.chosenCharacter = selectedClass;
        GameStateListener.resetStateVariables();
        return true;
    }
}
