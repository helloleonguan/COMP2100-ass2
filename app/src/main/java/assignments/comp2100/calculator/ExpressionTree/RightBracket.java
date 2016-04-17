package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Class for ')' token
 */
public class RightBracket extends UnaryOperator {
    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public int getPrecedence() { return LeftBracket.BRACKET_PRECEDENCE; }
}
