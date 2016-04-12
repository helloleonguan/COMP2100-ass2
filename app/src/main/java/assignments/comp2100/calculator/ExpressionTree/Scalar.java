package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class Scalar extends Expression {
    private float value;
    private static final int SCALAR_PRECEDENCE = LeftBracket.BRACKET_PRECEDENCE + 1;

    public Scalar(float value) {
        super(SCALAR_PRECEDENCE);
        this.value = value;
    }

    public float evaluate() {
        return value;
    }

    @Override
    public Expression getNode() {
        return parent;
    }

    @Override
    public Expression insertExpression(Expression expr) {
        throw new RuntimeException();
    }
}
