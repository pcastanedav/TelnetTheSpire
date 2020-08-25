package telnetthespire.commands;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Utils {

    public static final Predicate<String> isInteger = Pattern.compile("[0-9]+").asPredicate();
    public static final Predicate<String> isValidButton = Pattern.compile("(LEFT|RIGHT)").asPredicate();

    public static boolean isInDungeon() {
    return CardCrawlGame.mode == CardCrawlGame.GameMode.GAMEPLAY
        && AbstractDungeon.isPlayerInDungeon()
        && AbstractDungeon.currMapNode != null;
    }

}
