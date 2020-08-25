package telnetthespire.commands;

import telnetthespire.InvalidCommandException;
import telnetthespire.commands.annotations.Argument;
import telnetthespire.commands.annotations.Usage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static telnetthespire.commands.Utils.isInteger;

public class Parser {

    private final ArrayList<String> _tokens;

    public Parser(String[] tokens) {
        _tokens = Arrays.stream(tokens).collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<String> getCommand () {
        return getToken(0);
    }

    public Optional<String> getToken(int index) {
        return index < 0 || index >= _tokens.size()
            ? Optional.empty()
            : Optional.ofNullable(_tokens.get(index));
    }

    public static Optional<Integer> parseInt (String token) {
        return isInteger.test(token)
            ? Optional.of(Integer.parseInt(token))
            : Optional.empty();
    }

}

/*
    public int count () {
        return (int) _tokens.stream().count();
    }

    public static class ClickParams {
        public float x = 0;
        public float y = 0;
        public int timeout = 100;
        public String button;
    }

    private float parseFloat (String token) throws InvalidCommandException {
        try {
            return Float.parseFloat(token);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(new String[]{_command}, InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, token);
        }
    }



    public ClickParams toClickParams() throws InvalidCommandException {

        ClickParams click = new ClickParams();

        if (_tokens.size() < 3)
            throw new InvalidCommandException(new String[]{_command}, InvalidCommandException.InvalidCommandFormat.MISSING_ARGUMENT);
        click.x = parseFloat(_tokens.get(1));
        click.y = parseFloat(_tokens.get(2));
        click.button = _tokens.get(0);
        if (!isValidButton.test(click.button))
            throw new InvalidCommandException(new String[]{_command}, InvalidCommandException.InvalidCommandFormat.INVALID_ARGUMENT, click.button);
        if (_tokens.size() > 3)
            click.timeout = parseInt(_tokens.get(3));
        if (click.timeout < 0)
            throw new InvalidCommandException(new String[]{_command}, InvalidCommandException.InvalidCommandFormat.OUT_OF_BOUNDS, String.valueOf(click.timeout));

        return click;

    }

    public int toIndex() throws InvalidCommandException {
        return parseInt(toString());
    }

    public int toIndex(int index) throws InvalidCommandException {
        return parseInt(_tokens.get(index));
    }

    public String toString() {
        return _tokens.stream().reduce("", (string, token) -> string + " " + token);
    }

    public String toString(int index) {
       return _tokens.get(index);
    }
    */
