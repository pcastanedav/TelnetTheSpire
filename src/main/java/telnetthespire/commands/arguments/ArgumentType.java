package telnetthespire.commands.arguments;

import java.util.EnumSet;

public enum ArgumentType {
    TEXT, NATURAL, FLOAT;
    public static final EnumSet<ArgumentType> ALL_TYPES =java.util.EnumSet.allOf(ArgumentType.class);
}
