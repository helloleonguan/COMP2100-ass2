package assignments.comp2100.calculator.Test_Tree_Parsing;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class TreeParser {
    private static final String inputString = "4 + 4 * 2";

    public static void main(String[] args) {
        ExpressionTree tree = new ExpressionTree(-1);
        ExpressionTree currentTree = tree;
        String[] inputs = inputString.split(" ");

        for (String expression : inputs) {
            currentTree = currentTree.addNode(expression);
        }

        System.out.println(currentTree.getAncestor().evaluate());
    }
}
