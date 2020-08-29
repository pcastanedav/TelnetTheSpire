package telnetthespire.commands;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ErrorNode;
import telnetthespire.InvalidCommandException;
import telnetthespire.commands.arguments.ArgumentType;
import telnetthespire.commands.parsers.CommandParser;
import telnetthespire.commands.parsers.StSJargonBaseListener;
import telnetthespire.commands.parsers.StSJargonParser;

import java.util.Vector;

public class JargonListener extends StSJargonBaseListener {

    private CommandParser parser;
    private Vector<Object> arguments;

    private Vector<Command> order;

    public JargonListener() {
    }

    public Vector<Command> getOrder () {
       return order;
    }

    @Override
    public void enterOrder(StSJargonParser.OrderContext ctx) {
        order = new Vector<>(ctx.getChildCount());
    }

    @Override
    public void enterCommand(StSJargonParser.CommandContext ctx)  {

        parser = getParser(ctx.commandName().TEXTARG().getText().toLowerCase());

        if (ctx.argument().size() < parser.getMinimumArgumentCount())
            throw parser.invalidUsage(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT);

        arguments = new Vector<>(ctx.argument().size());

    }

    @Override
    public void exitCommand(StSJargonParser.CommandContext ctx) {
        order.add(parser.parse(arguments));
    }

    @Override
    public void enterImplicit(StSJargonParser.ImplicitContext ctx) {

        if (ctx.argument().size() < parser.getMinimumArgumentCount())
            throw parser.invalidUsage(InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT);

        arguments = new Vector<>(ctx.argument().size());
    }

    @Override
    public void exitImplicit(StSJargonParser.ImplicitContext ctx) {
        order.add(parser.parse(arguments));
    }

    @Override
    public void enterNaturalArgument(StSJargonParser.NaturalArgumentContext ctx) {

        if (!parser.getArgumentSignature(arguments.size()).contains(ArgumentType.NATURAL))
            throw parser.invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, ctx.getText());

        arguments.add(Integer.parseInt(ctx.getText()));

    }

    @Override
    public void enterTextArgument(StSJargonParser.TextArgumentContext ctx) {

        if (!parser.getArgumentSignature(arguments.size()).contains(ArgumentType.TEXT))
            throw parser.invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, ctx.getText());

        arguments.add(ctx.getText());
    }

    @Override
    public void enterFloatArgument(StSJargonParser.FloatArgumentContext ctx) {

        if (!parser.getArgumentSignature(arguments.size()).contains(ArgumentType.FLOAT))
            throw parser.invalidUsage(InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, ctx.getText());

        arguments.add(Float.parseFloat(ctx.getText()));
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        throw new ParseCancellationException(new InvalidCommandException("Error parsing command sent: " + node.toString() + ". Possible commands: " + CommandParserRegister.getAvailableCommandNames()));
    }

    private CommandParser getParser(String name) {
        return CommandParserRegister.getParser(name).orElseThrow(() ->
            new ParseCancellationException(
                new InvalidCommandException(
                    "Invalid command: " + name + ". Possible commands: " + CommandParserRegister.getAvailableCommandNames()
                )
            )
        );
    }
}
