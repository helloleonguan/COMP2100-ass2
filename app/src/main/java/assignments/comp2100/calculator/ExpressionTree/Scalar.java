package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Class for any number token
 */
public class Scalar extends ExpressionTree {
    private float value;
    private static final int SCALAR_PRECEDENCE = LeftBracket.BRACKET_PRECEDENCE - 1;

    public Scalar(float value) { this.value = value; }

    public float evaluate() {
        return value;
    }

    @Override
    public ExpressionTree getActiveNode() {
        return parent;
    }

    @Override
    public ExpressionTree insertExpression(ExpressionTree expr) {
        if (parent != null) {
            parent.appendExpression(expr);
        }
        return expr.insertExpression(this);
    }

    @Override
    public ExpressionTree appendExpression(ExpressionTree expr) {
        throw new RuntimeException();
    }

    @Override
    public int getPrecedence() { return SCALAR_PRECEDENCE; }
}
