package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class Scalar extends Expression {
    private float value;

    public Scalar(float value) {
        super(15);
        this.value = value;
        hasExpression = true;
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
