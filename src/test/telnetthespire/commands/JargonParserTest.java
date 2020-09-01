package telnetthespire.commands;

import org.junit.jupiter.api.Test;
import telnetthespire.commands.parsers.StSJargonLexer;

import java.nio.CharBuffer;

import static org.junit.jupiter.api.Assertions.*;

class JargonParserTest {

    @Test
    void prepareInput() {
        assertEquals(CharBuffer.wrap(".play"), JargonParser.prepareInput("play"),
    "Start symbol is not being added");
        assertEquals(CharBuffer.wrap(".start"), JargonParser.prepareInput(".start"),
                "Start symbol is not being added");
    }

    @Test
    void createLexer() {
        StSJargonLexer lexer = JargonParser.createLexer("");
        String[] rules = lexer.getRuleNames();
    }
}