package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 16/04/2016.
 */
public class Exponential extends UnaryOperator {
    @Override
    public float evaluate() {
        return (float) Math.exp(operand.evaluate());
    }

    @Override
    public int getPrecedence() {
        return UNARY_PRECEDENCE;
    }
}
