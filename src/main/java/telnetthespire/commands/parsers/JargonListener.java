package telnetthespire.commands.parsers;

import org.apache.commons.lang3.tuple.Pair;
import java.util.Optional;
import java.util.Vector;

public class JargonListener extends StSJargonBaseListener {

    private Vector<Pair<String, Optional<Vector<Object>>>> order;

    public Vector<Pair<String, Optional<Vector<Object>>>> getOrder () {
        return order;
    }

    @Override
    public void enterOrder(StSJargonParser.OrderContext ctx) {
        order = new Vector<>(ctx.getChildCount());
    }

    @Override
    public void enterCommand(StSJargonParser.CommandContext ctx) {
        order.add(Pair.of(
            ctx.commandName().WORD().getText().toLowerCase(),
            createArgumentsVector(ctx.argument().size())
        ));
    }

    @Override
    public void enterImplicit(StSJargonParser.ImplicitContext ctx) {
        order.add(Pair.of(
            order.lastElement().getKey(),
            createArgumentsVector(ctx.argument().size())
        ));
    }

    @Override
    public void enterNaturalArgument(StSJargonParser.NaturalArgumentContext ctx) {
        order.lastElement().getValue().ifPresent(args -> args.add(Integer.parseInt(ctx.getText())));
    }

    @Override
    public void enterTextArgument(StSJargonParser.TextArgumentContext ctx) {
        order.lastElement().getValue().ifPresent(args -> args.add(ctx.getText()));
    }

    @Override
    public void enterFloatArgument(StSJargonParser.FloatArgumentContext ctx) {
        order.lastElement().getValue().ifPresent(args -> args.add(Float.parseFloat(ctx.getText())));
    }

    private Optional<Vector<Object>> createArgumentsVector(int count) {
        return count > 0
            ? Optional.of(new Vector<>(count))
            : Optional.empty();
    }

}
