package telnetthespire.commands.parsers;

import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.CharBuffer;

public class JargonParser {

    private final JargonListener listener = new JargonListener();

    public JargonParser(String input) {

        CodePointBuffer buffer = CodePointBuffer.withChars(CharBuffer.wrap(input.toCharArray()));
        StSJargonLexer lexer = new StSJargonLexer(CodePointCharStream.fromBuffer(buffer));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StSJargonParser parser = new StSJargonParser(tokens);
        ParseTree tree = parser.order();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

    }

    public JargonListener getListener() {
        return listener;
    }

}
