package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 */
public abstract class UnaryOperator extends Expression {
    protected Expression operand;

    public UnaryOperator(int priority) {
        super(priority);
    }

    public Expression insertExpression(Expression expr) {
        if (expr.getParent() != null) {
            expr.getParent().insertExpression(this);
            expr.setParent(this);
            operand = expr;
        }
        if (operand != null) {
            operand.setParent(null);
            expr.insertExpression(operand);
        }
        operand = expr;
        expr.setParent(this);
        return expr;
    }
}
