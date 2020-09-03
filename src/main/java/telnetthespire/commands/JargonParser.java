package telnetthespire.commands;

import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.parsers.StSJargonLexer;
import telnetthespire.commands.parsers.StSJargonParser;

import java.nio.CharBuffer;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class JargonParser {


    public static Vector<Command> parse(String input) throws InvalidCommandException {
        try {
            return processOrder(input).getOrder();
        } catch (ParseCancellationException e) {
            throw (InvalidCommandException) e.getCause();
        }
    }

    public static JargonListener processOrder(String input) {
        JargonListener listener = new JargonListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, createTree(input));
        return listener;
    }

    public static ParseTree createTree (String input) {
        return createParser(input).order();
    }

    public static StSJargonParser createParser (String input) {
        return new StSJargonParser(new CommonTokenStream(createLexer(input)));
    }

    public static StSJargonLexer createLexer(String input) {
         return new StSJargonLexer(
             CodePointCharStream.fromBuffer(
                 CodePointBuffer.withChars(prepareInput(input))));
    }

    public static CharBuffer prepareInput (String input) {
        return (CharBuffer) createBuffer(isMissingStartSymbol.test(input), input.length())
            .append(input)
            .rewind();
    }

    private static CharBuffer createBuffer (boolean missingStartSymbol, int inputLength) {
        CharBuffer buffer = CharBuffer.allocate(missingStartSymbol ? inputLength + 1 : inputLength);
        return missingStartSymbol
            ? buffer.append(START_SYMBOL)
            : buffer;
    }

    private static final char START_SYMBOL = '.';
    private static final Predicate<String> isMissingStartSymbol = Pattern.compile("^[^" + START_SYMBOL + "]").asPredicate();
}
