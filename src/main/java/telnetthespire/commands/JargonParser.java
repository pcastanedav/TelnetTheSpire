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

public class JargonParser {

    static final char START_SYMBOL = '.';

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
        CommonTokenStream tokens = new CommonTokenStream(createLexer(input));
        StSJargonParser parser = new StSJargonParser(tokens);
        return parser.order();
    }

    public static StSJargonLexer createLexer(String input) {
         return new StSJargonLexer(
             CodePointCharStream.fromBuffer(
                 CodePointBuffer.withChars(prepareInput(input))));
    }

    public static CharBuffer prepareInput (String rawInput) {
        boolean missingStartSymbol = !rawInput.startsWith(String.valueOf(START_SYMBOL));
        int bufferSize = rawInput.length() + (missingStartSymbol ? 1 : 0);
        CharBuffer buffer = CharBuffer.allocate(bufferSize);
        if (missingStartSymbol) buffer.append(START_SYMBOL);
        buffer.append(rawInput).rewind();
        return buffer;
    }

}
