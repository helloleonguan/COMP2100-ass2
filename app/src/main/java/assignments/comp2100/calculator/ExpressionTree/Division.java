package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 14/04/2016.
 *
 * Class for '/' token
 */
public class Division extends BinaryOperator {
    public Division() {
        super(Multiplication.MULTIPLICATION_PRECEDENCE);
    }

    public float evaluate() {
        return left.evaluate() / right.evaluate();
    }
}
