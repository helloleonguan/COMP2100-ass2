package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Class for '+' token
 */
public class Addition extends BinaryOperator {
    static final int ADDITION_PRECEDENCE = 1;

    public float evaluate() {
        return left.evaluate() + right.evaluate();
    }

    @Override
    public int getPrecedence() { return ADDITION_PRECEDENCE; }
}

