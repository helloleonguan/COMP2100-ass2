package assignments.comp2100.calculator.ExpressionTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Nathan F. Elazar on 3/04/2016.
 *
 * Abstract class for all operators which have 1 argument.
 */
public class UnaryOperator extends ExpressionTree {
    protected static final int UNARY_PRECEDENCE = Integer.MAX_VALUE;
    protected ExpressionTree operand;
    protected Method operation;
    protected static HashMap<String, Method> derivativeMap = new HashMap<>();
    static {
        try {
            derivativeMap.put("exp", UnaryOperator.class.getDeclaredMethod("dExp"));
            derivativeMap.put("log", UnaryOperator.class.getDeclaredMethod("dLog"));
            derivativeMap.put("sin", UnaryOperator.class.getDeclaredMethod("dSin"));
            derivativeMap.put("cos", UnaryOperator.class.getDeclaredMethod("dCos"));
            derivativeMap.put("tan", UnaryOperator.class.getDeclaredMethod("dTan"));
        } catch (Exception e) {
            System.err.println("could not initialize unary derivativeMap");
        }
    }

    UnaryOperator() {}

    UnaryOperator(Operation op) {
        this.operation = op.operation;
        this.precedence = op.precedence;
    }

    //Derivatives
    ExpressionTree dExp() {
        UnaryOperator exp = new UnaryOperator(ExpressionTree.tokenParser.get("exp"));
        exp.insertExpression(operand.getClone());
        return exp;
    }
    ExpressionTree dLog() {
        BinaryOperator inverse = new BinaryOperator(ExpressionTree.tokenParser.get("/"));
        inverse.insertExpression(new Scalar(1));
        inverse.insertExpression(operand.getClone());
        return inverse;
    }
    ExpressionTree dSin() {
        UnaryOperator cos = new UnaryOperator(ExpressionTree.tokenParser.get("cos"));
        cos.insertExpression(operand.getClone());
        return cos;
    }
    ExpressionTree dCos() {
        BinaryOperator n_sin = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        n_sin.insertExpression(new Scalar(-1));
        UnaryOperator sin = new UnaryOperator(ExpressionTree.tokenParser.get("sin"));
        sin.insertExpression(operand.getClone());
        n_sin.insertExpression(sin);
        return n_sin;
    }
    ExpressionTree dTan() {
        BinaryOperator cos_2 = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
        UnaryOperator cos = new UnaryOperator(ExpressionTree.tokenParser.get("cos"));
        cos.insertExpression(operand.getClone());
        cos_2.insertExpression(cos);
        cos_2.insertExpression(cos.getClone());
        BinaryOperator sec_2 = new BinaryOperator(ExpressionTree.tokenParser.get("/"));
        sec_2.insertExpression(new Scalar(1));
        sec_2.insertExpression(cos_2);
        return sec_2;
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
    public ExpressionTree getDerivative() {
        try {
            BinaryOperator dx = new BinaryOperator(ExpressionTree.tokenParser.get("*"));
            dx.insertExpression(operand.getDerivative());
            dx.insertExpression((ExpressionTree) derivativeMap.get(operation.getName()).invoke(this));
            return dx;
        } catch (Exception e) {
            System.err.println("UnaryOperator could not get derivative");
            return null;
        }
    }

    @Override
    public ExpressionTree getSimplified() {
        operand = operand.getSimplified();
        return this;
    }

    @Override
    public float evaluate() {
        try {
            try {
                return (float) operation.invoke(null, operand.evaluate());
            } catch (IllegalAccessException e) {
                return 0;
            }
        } catch (InvocationTargetException e) {
            return 0;
        }
    }

    @Override
    public float evaluate(float x) {
        try {
            try {
                return (float) operation.invoke(null, operand.evaluate(x));
            } catch (IllegalAccessException e) {
                return 0;
            }
        } catch (InvocationTargetException e) {
            return Float.NaN;
        }
    }

    @Override
    int getPrecedence() {
        return precedence;
    }

    @Override
    ExpressionTree getClone() {
        UnaryOperator clone = new UnaryOperator(new Operation(operation, precedence));
        clone.insertExpression(operand.getClone());
        return clone;
    }

    @Override
    public String toString() {
        return OperationDatabase.reverseTokenizer.get(operation.getName()) + " ( " + operand.toString() + " ) ";
    }
}
