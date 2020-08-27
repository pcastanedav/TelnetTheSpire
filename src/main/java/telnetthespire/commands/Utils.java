package telnetthespire.commands;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Utils {

    public static final Predicate<String> isInteger = Pattern.compile("[0-9]+").asPredicate();
    public static final Predicate<String> isValidButton = Pattern.compile("(LEFT|RIGHT)").asPredicate();
    private static final HashMap<String, InputAction> keyCodes = new HashMap<>();

    static {
        keyCodes.put("CONFIRM", InputActionSet.confirm);
        keyCodes.put("CANCEL", InputActionSet.cancel);
        keyCodes.put("MAP", InputActionSet.map);
        keyCodes.put("DECK", InputActionSet.masterDeck);
        keyCodes.put("DRAW_PILE", InputActionSet.drawPile);
        keyCodes.put("DISCARD_PILE", InputActionSet.discardPile);
        keyCodes.put("EXHAUST_PILE", InputActionSet.exhaustPile);
        keyCodes.put("END_TURN", InputActionSet.endTurn);
        keyCodes.put("UP", InputActionSet.up);
        keyCodes.put("DOWN", InputActionSet.down);
        keyCodes.put("LEFT", InputActionSet.left);
        keyCodes.put("RIGHT", InputActionSet.right);
        keyCodes.put("DROP_CARD", InputActionSet.releaseCard);
        keyCodes.put("CARD_1", InputActionSet.selectCard_1);
        keyCodes.put("CARD_2", InputActionSet.selectCard_2);
        keyCodes.put("CARD_3", InputActionSet.selectCard_3);
        keyCodes.put("CARD_4", InputActionSet.selectCard_4);
        keyCodes.put("CARD_5", InputActionSet.selectCard_5);
        keyCodes.put("CARD_6", InputActionSet.selectCard_6);
        keyCodes.put("CARD_7", InputActionSet.selectCard_7);
        keyCodes.put("CARD_8", InputActionSet.selectCard_8);
        keyCodes.put("CARD_9", InputActionSet.selectCard_9);
        keyCodes.put("CARD_10", InputActionSet.selectCard_10);
    }

    public static boolean isInDungeon() {
        return CardCrawlGame.mode == CardCrawlGame.GameMode.GAMEPLAY
            && AbstractDungeon.isPlayerInDungeon()
            && AbstractDungeon.currMapNode != null;
    }

    public static Optional<Integer> getKeyCode(String keyName) {
        return keyCodes.containsKey(keyName)
            ? Optional.of((Integer) ReflectionHacks.getPrivate(keyCodes.get(keyName), InputAction.class, "keycode"))
            : Optional.empty();
    }

}
