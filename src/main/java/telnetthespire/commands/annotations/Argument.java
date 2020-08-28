package telnetthespire.commands.annotations;

import telnetthespire.commands.arguments.ArgumentType;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Arguments.class)
public @interface Argument {
    String name();
    ArgumentType type() default ArgumentType.TEXT;
    boolean required() default false;
}
