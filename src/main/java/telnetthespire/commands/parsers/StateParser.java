package telnetthespire.commands.parsers;

import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;
import telnetthespire.commands.handlers.State;
import java.util.Vector;

@Name("state")
public class StateParser extends CommandParser {

    public StateParser() {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Command parse(Vector<Object> arguments) {
        return new State(this);
    }

}