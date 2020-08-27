package telnetthespire.commands;

import telnetthespire.InvalidCommandException;

public interface Command {
    boolean executeIfAvailable() throws InvalidCommandException;
    boolean execute() throws InvalidCommandException;
}
