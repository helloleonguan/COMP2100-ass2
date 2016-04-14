package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Abstract class for all operators which have 1 argument.
 */
public abstract class UnaryOperator extends ExpressionTree {
    protected ExpressionTree operand;

    public ExpressionTree insertExpression(ExpressionTree expr) {
        if (operand != null) {
            if (getParent() != null) {
                getParent().appendExpression(expr);
            }
            return expr.insertExpression(this);
        } else {
            operand = expr;
            expr.setParent(this);
            return expr;
        }
    }

    @Override
    ExpressionTree appendExpression(ExpressionTree expr) {
        operand = expr;
        expr.setParent(this);
        return expr;
    }
}
