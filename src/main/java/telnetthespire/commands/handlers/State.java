package telnetthespire.commands.handlers;

import telnetthespire.TelnetTheSpire;
import telnetthespire.commands.Command;
import telnetthespire.commands.annotations.Name;

import java.util.Optional;
import java.util.Vector;

@Name("state")
public class State extends Command {

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean execute(Optional<Vector<Object>> parser) {
        TelnetTheSpire.mustSendGameState = true;
        return false;
    }
}