package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Class for '(' token
 */
public class LeftBracket extends UnaryOperator {
    public static final int BRACKET_PRECEDENCE = 0;

    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public ExpressionTree getScope() {
        return this;
    }

    @Override
    public int getPrecedence() { return BRACKET_PRECEDENCE; }
}
