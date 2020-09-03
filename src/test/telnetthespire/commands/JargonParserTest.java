package telnetthespire.commands;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;

import static org.junit.jupiter.api.Assertions.*;

class JargonParserTest {

    private ByteArrayOutputStream errContent;
    private static final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void prepareInput() {
        assertEquals(CharBuffer.wrap(".play"), JargonParser.prepareInput("play"),
    "Start symbol is not being added");
        assertEquals(CharBuffer.wrap(".start"), JargonParser.prepareInput(".start"),
                "Start symbol is not being added");
    }

    @Test
    void emptyOrder () {
        assertOrder(
                "An empty order is not expected",
                "",
                "(order (command commandName))",
                "mismatched input '<EOF>' expecting '.'"
        );
    }

    @Test
    void singleCommand () {
        assertOrder(
            "",
            "play",
            "(order (command (commandName . play)))",
            ""
        );
    }

    private void assertOrder (String message, String order,String expectedTree, String expectedError) {
        assertAll(
            message,
            () -> {
                ParseTree tree = JargonParser.createTree(order);
                String parsed = tree.toStringTree(JargonParser.createParser(order));
                assertEquals(expectedTree, parsed);
            },
                () -> assertTrue(errContent.toString().contains(expectedError))
        );
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }
}