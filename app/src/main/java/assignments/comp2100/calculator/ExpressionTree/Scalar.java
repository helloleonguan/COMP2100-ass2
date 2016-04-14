package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Class for any number token
 */
public class Scalar extends ExpressionTree {
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
    public ExpressionTree getActiveNode() {
        return parent;
    }

    @Override
    public ExpressionTree insertExpression(ExpressionTree expr) {
        throw new RuntimeException();
    }

    @Override
    public ExpressionTree addNode(ExpressionTree expr) {
        return parent != null
                ? parent.insertExpression(expr)
                : expr.insertExpression(this);
    }
}
