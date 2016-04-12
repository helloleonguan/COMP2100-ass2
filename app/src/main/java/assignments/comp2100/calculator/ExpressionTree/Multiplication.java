package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class Multiplication extends BinaryOperator {
    private static final int MULTIPLICATION_PRIORITY = 2;

    public Multiplication() {
        super(MULTIPLICATION_PRIORITY);
        hasExpression = true;
    }

    public float evaluate() {
        return left.evaluate() * right.evaluate();
    }
}
