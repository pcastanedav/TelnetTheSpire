package telnetthespire.commands.arguments;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.commons.lang3.tuple.Pair;

public class StartArguments {
    public AbstractPlayer.PlayerClass SelectedClass;
    public Integer AscensionLevel;
    public Long Seed;
    public Boolean SeedSet;
    public Boolean IsTrialSeed;
}
