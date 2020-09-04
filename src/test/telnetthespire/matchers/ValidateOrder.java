package telnetthespire.matchers;

import org.antlr.v4.runtime.tree.ParseTree;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.internal.SelfDescribingValue;
import telnetthespire.commands.JargonParser;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

public class ValidateOrder extends TypeSafeMatcher<String> {

    private ByteArrayOutputStream errorStream;
    private Pattern expectedError = Pattern.compile("^$", Pattern.DOTALL);
    private String parsedOrder;
    private String expectedTree;
    private boolean failedToMatchError = false;

    public ValidateOrder(String expectedTree) {
        this.expectedTree = expectedTree;
    }

    public ValidateOrder watching(ByteArrayOutputStream error) {
        errorStream = error;
        return this;
    }

    public ValidateOrder withErrorMatching (String pattern) {
        expectedError = Pattern.compile(".*" + pattern + ".*", Pattern.DOTALL);
        return this;
    }

    @Override
    protected boolean matchesSafely(String order) {
        ParseTree tree = JargonParser.createTree(order);
        parsedOrder = tree.toStringTree(JargonParser.createParser(order));
        String error = errorStream.toString();
        failedToMatchError = !expectedError.matcher(error).matches();
        return expectedTree.contentEquals(parsedOrder) && !failedToMatchError;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedTree);
        description.appendText("\nParsed Tree: ");
        description.appendText(parsedOrder);
        if (failedToMatchError) {
            description.appendText("\nExpected Error: ");
            description.appendValue(expectedError);
            description.appendText("\nActual Error: ");
            description.appendText(errorStream.toString());
        }
    }

    public static ValidateOrder validateOrder(String expectedTree) {
        return new ValidateOrder(expectedTree);
    }
}