package telnetthespire.commands.arguments;

public class PotionArguments {
    public enum Type { USE, DISCARD }
    public Type Action;
    public Integer Target;
    public Integer Monster;
}
