package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TrialHelper;
import com.megacrit.cardcrawl.random.Random;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.misc.Triple;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.arguments.StartArguments;
import telnetthespire.commands.handlers.Start;

import java.util.Vector;
import java.util.stream.Stream;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("start")
@Argument(name="SelectedClass", type = ArgumentType.TEXT, required = true)
@Argument(name="AscensionLevel", type = ArgumentType.NATURAL)
@Argument(name="Seed")
@Usage("\nUsage: start ironclad|silent|defect|watcher [AscensionLevel] [seed]")
public class StartParser extends CommandParser {

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        StartArguments startArguments = new StartArguments();
        startArguments.SelectedClass = getSelectedClass(arguments);
        startArguments.AscensionLevel = getAscensionLevel(arguments);

        Triple<Boolean, Boolean, Long> seed = getSeed(arguments);
        startArguments.SeedSet  = seed.a;
        startArguments.IsTrialSeed = seed.b;
        startArguments.Seed = seed.c;

        return new Start(this, startArguments);
    }

    @Override
    public boolean isAvailable() {
        return !isInDungeon()
                && !CardCrawlGame.characterManager.anySaveFileExists();
    }

    private AbstractPlayer.PlayerClass getSelectedClass (Vector<Object> arguments) throws ParseCancellationException {

        if (arguments.size() < 1)
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT);

        Object argument = arguments.firstElement();
        if (!(argument instanceof String))
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, argument.toString());

        String character = (String) argument;
        return character.equalsIgnoreCase("silent")
            ? AbstractPlayer.PlayerClass.THE_SILENT
            : Stream.of(AbstractPlayer.PlayerClass.values())
                .filter(playerClass -> playerClass.name().equalsIgnoreCase(character))
                .findFirst()
                .orElseThrow(() -> invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, character));
    }

    private Integer getAscensionLevel (Vector<Object> arguments) throws ParseCancellationException {

        if (arguments.size() < 2) return 0;

        Object argument = arguments.get(1);
        if (!(argument instanceof Integer))
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, argument.toString());

        Integer ascensionLevel = (Integer) argument;
        if (ascensionLevel < 0 || ascensionLevel > 20) {
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, ascensionLevel.toString());
        }

        return ascensionLevel;
    }

    private Triple<Boolean, Boolean, Long> getSeed (Vector<Object> arguments) throws ParseCancellationException {

        if (arguments.size() < 3) return new Triple<>(false, false, SeedHelper.generateUnoffensiveSeed(new Random(System.nanoTime())));

        Object argument = arguments.get(2);
        if (!(argument instanceof String))
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, argument.toString());

        String seedString = (String) argument;

        if (!seedString.toUpperCase().matches("^[A-Z0-9]+$"))
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, seedString);

        boolean isTrialSeed = TrialHelper.isTrialSeed(seedString);

        return new Triple<>(!isTrialSeed, isTrialSeed, SeedHelper.getLong(seedString));
    }

}
