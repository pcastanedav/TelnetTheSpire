package telnetthespire.commands.parsers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.annotations.Usage;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.arguments.PotionArguments;
import telnetthespire.commands.handlers.Potion;

import java.util.Vector;

import static telnetthespire.commands.Utils.isInDungeon;

@Name("potion")
@Argument(name="Action",required = true)
@Argument(name="Target",required = true)
@Argument(name="Monster", type= ArgumentType.NATURAL)
@Usage("\nUsage: potion use|discard potion_index [TargetIndex]")
public class PotionParser extends CommandParser {

    @Override
    public boolean isAvailable() {
        return isInDungeon()
                && AbstractDungeon.player.potions.stream().anyMatch(potion -> !(potion instanceof PotionSlot));
    }

    @Override
    public Command parse(Vector<Object> arguments) throws ParseCancellationException {
        return new Potion(this, new PotionArguments(){{
           Action = getEnumValue(PotionArguments.Type.class, (String) arguments.get(0));
           Target = (Integer) arguments.get(1);
           Monster = arguments.size() < 3 ? -1 : (Integer) arguments.get(2);
        }});
    }

}
