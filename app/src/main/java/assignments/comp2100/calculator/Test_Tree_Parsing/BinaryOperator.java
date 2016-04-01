package assignments.comp2100.calculator.Test_Tree_Parsing;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class BinaryOperator extends ExpressionTree {
    private String operator;
    private boolean hasOperator = false;

    public BinaryOperator(int priority) {
        super(priority);
    }

    public static BinaryOperator parseStringToOperator(String operator) {
        if (operator.equals("+")) {
            return new Addition();
        } else if (operator.equals("*")) {
            return new Multiplication();
        }
        return null;
    }

    public void setOperator(String operator) {
        this.operator = operator;
        hasOperator = true;
    }
}