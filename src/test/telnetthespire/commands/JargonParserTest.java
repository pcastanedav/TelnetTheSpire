package telnetthespire.commands;

import org.antlr.v4.runtime.tree.ParseTree;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static telnetthespire.matchers.ValidateOrder.validateOrder;

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
        assertThat(
            "An empty order produces a parsing error",
            "",
            validateOrder("(order (command commandName))")
                .watching(errContent)
                .withErrorMatching("input '<EOF>' expecting '.'")
        );
    }

    @Test
    void singleCommand () {
        assertThat(
            "Command with no arguments",
            "play",
            validateOrder("(order (command (commandName . play)))")
                .watching(errContent)
        );
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }
}