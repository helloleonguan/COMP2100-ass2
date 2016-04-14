package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Abstract class for all operators which have 2 arguments.
 * Contains code for inserting new expressions into a
 * node which has two children.
 */
public abstract class BinaryOperator extends ExpressionTree {
    protected ExpressionTree left;
    protected ExpressionTree right;

    /**
     * Given a new expression, this method inserts it
     * into either this binary operators left or right
     * operand, depending on the current state of this operator
     * @param expr New Expression to be added into the tree
     * @return The next active node
     */
    ExpressionTree insertExpression(ExpressionTree expr) {
        if (left == null) { //We assume left to right parsing
            left = expr;
            expr.setParent(this);
        } else if (right == null) {
            right = expr;
            expr.setParent(this);
        } else {
            if (expr.getPrecedence() <= getPrecedence()) {
                if (parent != null) {
                    getParent().insertExpression(expr);
                } else {
                    expr.insertExpression(this);
                }
            } else {
                expr.insertExpression(right);
                right = expr;
                expr.setParent(this);
            }
        }
        return expr.getActiveNode();
    }

    @Override
    ExpressionTree appendExpression(ExpressionTree expr) {
        right = expr;
        expr.setParent(this);
        return this;
    }
}