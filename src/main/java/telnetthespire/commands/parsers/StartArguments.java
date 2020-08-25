package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TrialHelper;
import com.megacrit.cardcrawl.random.Random;
import org.apache.commons.lang3.tuple.Pair;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.CommandArguments;
import telnetthespire.commands.annotations.Usage;

import java.util.Vector;
import java.util.stream.Stream;

@Usage("\nUsage: start ironclad|silent|defect|watcher [AscensionLevel] [seed]")
public class StartArguments extends CommandArguments {

    public StartArguments(String commandName, Vector<Object> arguments) {
        super(commandName, arguments);
    }

    public AbstractPlayer.PlayerClass getSelectedClass () throws InvalidCommandException {

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

    public Integer getAscensionLevel () throws InvalidCommandException {

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

    public Pair<Boolean, Long> getSeed () throws InvalidCommandException {

        if (arguments.size() < 3) return Pair.of(false, SeedHelper.generateUnoffensiveSeed(new Random(System.nanoTime())));

        Object argument = arguments.get(2);
        if (!(argument instanceof String))
            throw invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, argument.toString());

        String seedString = (String) argument;

        if (!seedString.toUpperCase().matches("^[A-Z0-9]+$"))
            throw invalidCommand(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, seedString);

        boolean seedSet = true;
        long seed = SeedHelper.getLong(seedString);
        boolean isTrialSeed = TrialHelper.isTrialSeed(seedString);
        if (isTrialSeed) {
            Settings.specialSeed = seed;
            Settings.isTrial = true;
            seedSet = false;
        }
        return Pair.of(seedSet, seed);
    }
}
