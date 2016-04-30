package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Class for any number token
 */
public class Scalar extends ExpressionTree {
    private float value;
    static final int SCALAR_PRECEDENCE = LeftBracket.BRACKET_PRECEDENCE + 1;

    Scalar() {}
    Scalar(float value) { this.value = value; }

    @Override
    public ExpressionTree getDerivative() { return new Scalar(0); }

    @Override
    public ExpressionTree getSimplified() { return this; }

    @Override
    public float evaluate() {
        return value;
    }

    @Override
    public ExpressionTree insertExpression(ExpressionTree expr) {
        if (parent != null) {
            return parent.insertExpression(expr);
        } else {
            return expr.insertExpression(this);
        }
    }

    @Override
    public int getPrecedence() { return SCALAR_PRECEDENCE; }

    @Override
    public ExpressionTree getClone() { return new Scalar(value); }

    @Override
    public String toString() { return String.valueOf(value); }

    float getValue() { return value; }
}
