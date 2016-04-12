package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 */
public class LeftBracket extends UnaryOperator {
    public static final int BRACKET_PRECEDENCE = 10;

    public LeftBracket() {
        super(BRACKET_PRECEDENCE);
    }

    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public Expression getContext() {
        return this;
    }
}
