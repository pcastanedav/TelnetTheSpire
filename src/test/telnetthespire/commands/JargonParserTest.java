package telnetthespire.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
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
    void commandWithNoArguments () {
        assertThat(
            "Command with no arguments",
            "play",
            validateOrder("(order (command (commandName . play)))")
                .watching(errContent)
        );
    }

    @Test
    void commandWithArguments () {

        assertThat(
           "Command with a natural argument",
           "choose 1",
            validateOrder("(order (command (commandName . choose) (argument (naturalArgument 1))))").watching(errContent)
        );
        assertThat(
                "Command with a float argument",
                "choose 1.3",
                validateOrder("(order (command (commandName . choose) (argument (floatArgument 1.3))))").watching(errContent)
        );
        assertThat(
                "Command with a text argument",
                "start silent",
                validateOrder("(order (command (commandName . start) (argument (textArgument silent))))").watching(errContent)
        );
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }
}