package telnetthespire.commands.handlers;
import telnetthespire.TelnetTheSpire;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.StateParser;

public class State extends CommandHandler<NoArguments> {

    public State(StateParser stateParser) {
        super(stateParser);
    }

    @Override
    public boolean execute() {
        TelnetTheSpire.mustSendGameState = true;
        return false;
    }

}