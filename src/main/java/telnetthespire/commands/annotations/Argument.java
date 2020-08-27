package telnetthespire.commands.annotations;

import telnetthespire.commands.arguments.ArgumentType;

import java.lang.annotation.*;
import java.util.EnumSet;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Arguments.class)
public @interface Argument {
    boolean required();
}
