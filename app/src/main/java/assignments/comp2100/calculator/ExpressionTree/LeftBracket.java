package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Class for '(' token
 */
public class LeftBracket extends UnaryOperator {
    public static final int BRACKET_PRECEDENCE = 0;

    private boolean isClosed = false;

    @Override
    ExpressionTree insertExpression(ExpressionTree expr) {
        if (expr instanceof RightBracket) {
            isClosed = true;
            return this;
        }
        if (isClosed) {
            if (parent != null) {
                return parent.insertExpression(expr);
            } else {
                return expr.insertExpression(this);
            }
        } else {
            return super.insertExpression(expr);
        }
    }

    @Override
    public ExpressionTree getDerivative() { return operand.getDerivative(); }

    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public int getPrecedence() { return BRACKET_PRECEDENCE; }

    @Override
    public ExpressionTree getClone() {
        LeftBracket clone = new LeftBracket();
        clone.insertExpression(operand.getClone());
        clone.isClosed = isClosed;
        return clone;
    }

    @Override
    public String toString() { return "( " + operand.toString() + " )"; }
}
