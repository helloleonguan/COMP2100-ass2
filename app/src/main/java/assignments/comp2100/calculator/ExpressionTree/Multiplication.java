package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Class for '*' token
 */
public class Multiplication extends BinaryOperator {
    static final int MULTIPLICATION_PRECEDENCE = 2;

    public Multiplication() {
        super(MULTIPLICATION_PRECEDENCE);
    }

    public float evaluate() {
        return left.evaluate() * right.evaluate();
    }
}
