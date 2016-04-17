package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 16/04/2016.
 *
 * Class for log function
 */
public class Logarithm extends UnaryOperator {

    @Override
    public float evaluate() {
        return (float) Math.log(operand.evaluate());
    }

    @Override
    public int getPrecedence() {
        return UNARY_PRECEDENCE;
    }
}
