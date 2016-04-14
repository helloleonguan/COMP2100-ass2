package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Class for ')' token
 */
public class RightBracket extends UnaryOperator {
    public RightBracket() {
        super(LeftBracket.BRACKET_PRECEDENCE);
    }

    @Override
    public ExpressionTree insertExpression(ExpressionTree expr) {
        super.insertExpression(expr);
        return getScope();
    }

    @Override
    public float evaluate() {
        return operand.evaluate();
    }

    @Override
    public ExpressionTree getActiveNode() {
        return getRoot();
    }
}
