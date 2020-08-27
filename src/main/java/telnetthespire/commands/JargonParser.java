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

    public static Vector<Command> parse(String raw) throws InvalidCommandException {
        String input = raw.startsWith(".") ? raw : "." + raw;
        CodePointBuffer buffer = CodePointBuffer.withChars(CharBuffer.wrap(input.toCharArray()));
        StSJargonLexer lexer = new StSJargonLexer(CodePointCharStream.fromBuffer(buffer));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StSJargonParser parser = new StSJargonParser(tokens);
        ParseTree tree = parser.order();
        ParseTreeWalker walker = new ParseTreeWalker();
        JargonListener listener = new JargonListener();
        try {
            walker.walk(listener, tree);
            return listener.getOrder();
        } catch (ParseCancellationException e) {
            throw (InvalidCommandException) e.getCause();
        }
    }
}
