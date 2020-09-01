package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.Abandon;
import telnetthespire.commands.Command;

import java.util.Vector;

import static telnetthespire.CommandExecutor.isInDungeon;

@Name("abandon")
public class AbandonParser extends CommandParser {

    @Override
    public boolean isAvailable() {
        return !isInDungeon()
                && CardCrawlGame.characterManager.anySaveFileExists();
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Abandon(this);
    }

}
