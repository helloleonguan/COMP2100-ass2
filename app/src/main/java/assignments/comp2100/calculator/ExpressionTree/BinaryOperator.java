package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public abstract class BinaryOperator extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperator(int priority) {
        super(priority);
    }

    public Expression insertExpression(Expression expr) {
        if (left == null) {
            if (expr.getParent() != null) {
                expr.getParent().insertExpression(this);
                return this;
            } else {
                left = expr;
                expr.setParent(this);
                return this;
            }
        } else {
            if (right != null) {
                right.setParent(null);
                expr.insertExpression(right);
            }
            right = expr;
            expr.setParent(this);
            return right.getNode();
        }
    }
}