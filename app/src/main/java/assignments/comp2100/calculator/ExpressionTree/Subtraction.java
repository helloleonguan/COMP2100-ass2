package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 14/04/2016.
 *
 * Class for '-' token
 */
public class Subtraction extends BinaryOperator {
    public float evaluate() {
        return left.evaluate() - right.evaluate();
    }

    @Override
    public int getPrecedence() { return Addition.ADDITION_PRECEDENCE; }
}
