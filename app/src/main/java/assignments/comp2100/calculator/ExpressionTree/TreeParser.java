package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class TreeParser {
    private static final String inputString = "( 4 * 4 )";

    public static void main(String[] args) {
        System.out.println(Expression.parseStringToTree(inputString).evaluate());
    }
}
