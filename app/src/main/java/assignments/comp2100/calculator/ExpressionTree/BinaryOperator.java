package assignments.comp2100.calculator.ExpressionTree;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Abstract class for all operators which have 2 arguments.
 * Contains code for inserting new expressions into a
 * node which has two children.
 */
public class BinaryOperator extends ExpressionTree {
    protected ExpressionTree left;
    protected ExpressionTree right;
    protected Method operation;
    protected static HashMap<String, Method> derivativeMap = new HashMap<>();
    static {
        try {
            derivativeMap.put("add", BinaryOperator.class.getDeclaredMethod("additionRule"));
            derivativeMap.put("sub", BinaryOperator.class.getDeclaredMethod("additionRule"));
            derivativeMap.put("mult", BinaryOperator.class.getDeclaredMethod("productRule"));
            derivativeMap.put("div", BinaryOperator.class.getDeclaredMethod("quotientRule"));
        } catch (Exception e) {
            System.err.println("could not initialize binary derivativeMap");
        }
    }

    BinaryOperator(Operation op) {
        this.operation = op.operation;
        this.precedence = op.precedence;
    }

    BinaryOperator(BinaryOperator other) {
        this.operation = other.getOperation();
        this.precedence = other.getPrecedence();
    }

    //Derivatives
    ExpressionTree additionRule() {
        BinaryOperator middle = new BinaryOperator(new Operation(operation, precedence));
        middle.insertExpression(left.getDerivative());
        middle.insertExpression(right.getDerivative());

        return middle;
    }

    ExpressionTree productRule() {
        BinaryOperator middle = new BinaryOperator(ExpressionTree.tokenParser.get("+"));
        BinaryOperator left = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        BinaryOperator right = new BinaryOperator(ExpressionTree.tokenParser.get("*"));

        left.insertExpression(this.left.getDerivative());
        left.insertExpression(this.right.getClone());

        right.insertExpression(this.left.getClone());
        right.insertExpression(this.right.getDerivative());

        middle.insertExpression(left);
        middle.insertExpression(right);

        return middle;
    }

    ExpressionTree quotientRule() {
        BinaryOperator middle = new BinaryOperator(ExpressionTree.tokenParser.get("-"));
        BinaryOperator left = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        BinaryOperator right = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        BinaryOperator quotient = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        BinaryOperator division = new BinaryOperator(ExpressionTree.tokenParser.get("/"));

        left.insertExpression(this.left.getDerivative());
        left.insertExpression(this.right);

        right.insertExpression(this.left);
        right.insertExpression(this.right.getDerivative());

        middle.insertExpression(left);
        middle.insertExpression(right);

        quotient.insertExpression(new BinaryOperator(right));
        quotient.insertExpression(new BinaryOperator(right));

        division.insertExpression(middle);
        division.insertExpression(quotient);

        return division;
    }

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
            return this;
        } else if (right == null) {
            right = expr;
            expr.setParent(this);
            return right;
        } else {
            if (expr.getPrecedence() <= getPrecedence()) {
                if (parent != null) {
                    return getParent().insertExpression(expr);
                } else {
                    return expr.insertExpression(this);
                }
            } else {
                expr.insertExpression(right);
                right = expr;
                expr.setParent(this);
                return right;
            }
        }
    }

    @Override
    public ExpressionTree getDerivative() {
        try {
            return (ExpressionTree) derivativeMap.get(operation.getName()).invoke(this);
        } catch (Exception e) {
            System.err.println("BinaryOperator failed to getDerivative");
            return null;
        }
    }

    @Override
    public ExpressionTree getSimplified() {
        left = left.getSimplified();
        right = right.getSimplified();
        if (operation.getName().equals("mult")) {
            if (left.getClass().equals(Scalar.class)) {
                if (Math.abs(((Scalar) left).getValue() - 1) < ExpressionTree.DELTA) {
                    return right;
                }
                if (Math.abs(((Scalar) left).getValue()) < ExpressionTree.DELTA) {
                    return new Scalar(0);
                }
            }
            if (right.getClass().equals(Scalar.class)) {
                if (Math.abs(((Scalar) right).getValue() - 1) < ExpressionTree.DELTA) {
                    return left;
                }
                if (Math.abs(((Scalar) right).getValue()) < ExpressionTree.DELTA) {
                    return new Scalar(0);
                }
            }
        } else if (operation.getName().equals("add") || operation.getName().equals("sub")) {
            if (left.getClass().equals(Scalar.class)) {
                if (Math.abs(((Scalar) left).getValue()) < ExpressionTree.DELTA) {
                    return right;
                }
            }
            if (right.getClass().equals(Scalar.class)) {
                if (Math.abs(((Scalar) right).getValue()) < ExpressionTree.DELTA) {
                    return left;
                }
            }
        }
        return this;
    }

    @Override
    public float evaluate() {
        try {
            return (float) operation.invoke(null, left.evaluate(), right.evaluate());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public float evaluate(float x) {
        try {
            return (float) operation.invoke(null, left.evaluate(x), right.evaluate(x));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    int getPrecedence() {
        return precedence;
    }

    @Override
    public ExpressionTree getClone() {
        BinaryOperator clone = new BinaryOperator(new Operation(operation, precedence));
        clone.insertExpression(left.getClone());
        clone.insertExpression(right.getClone());
        return clone;
    }

    @Override
    public String toString() {
        return left.toString() + " " + OperationDatabase.reverseTokenizer.get(operation.getName()) + " " + right.toString();
    }

    Method getOperation() { return operation; }
}