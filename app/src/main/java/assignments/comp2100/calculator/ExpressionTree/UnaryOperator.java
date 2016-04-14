package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Abstract class for all operators which have 1 argument.
 */
public abstract class UnaryOperator extends ExpressionTree {
    protected ExpressionTree operand;

    public UnaryOperator(int priority) {
        super(priority);
    }

    public ExpressionTree insertExpression(ExpressionTree expr) {
        if (operand != null) {
            operand.setParent(null);
            expr.insertExpression(operand);
        }
        operand = expr;
        expr.setParent(this);
        return expr;
    }
}
