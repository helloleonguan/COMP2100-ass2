package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 */
public class RightBracket extends UnaryOperator {
    private static final int RIGHT_BRACKET_PRECEDENCE = -20;

    public RightBracket() {
        super(LeftBracket.BRACKET_PRECEDENCE);
    }

    @Override
    public Expression insertExpression(Expression expr) {
        super.insertExpression(expr);
        return getContext();
    }

    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public Expression getNode() {
        return getRoot();
    }
}
