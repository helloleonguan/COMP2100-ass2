package assignments.comp2100.calculator.ExpressionTree;

import java.lang.reflect.Method;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Abstract class for all operators which have 1 argument.
 */
public class UnaryOperator extends ExpressionTree {
    protected static final int UNARY_PRECEDENCE = Integer.MAX_VALUE;
    protected ExpressionTree operand;
    protected Method operation;
    protected int precedence;

    UnaryOperator() {}

    UnaryOperator(Method operation, int precedence) {
        this.operation = operation;
        this.precedence = precedence;
    }

    @Override
    ExpressionTree insertExpression(ExpressionTree expr) {
        if (operand == null) {
            operand = expr;
            expr.setParent(this);
            return expr;
        } else {
            if (expr.getPrecedence() < getPrecedence()) {
                if (parent != null) {
                    return parent.insertExpression(expr);
                } else {
                    return expr.insertExpression(this);
                }
            } else {
                expr.insertExpression(operand);
                operand = expr;
                expr.setParent(this);
                return expr;
            }
        }
    }

    @Override
    public float evaluate() {
        try {
            return (float) operation.invoke(null, new Object[] {operand.evaluate()});
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    int getPrecedence() {
        return precedence;
    }
}
