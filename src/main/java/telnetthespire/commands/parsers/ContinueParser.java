package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.Continue;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("continue")
public class ContinueParser  extends CommandParser {

    @Override
    public boolean isAvailable() {
        return !isInDungeon()
                && CardCrawlGame.characterManager.anySaveFileExists();
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Continue(this);
    }

}
