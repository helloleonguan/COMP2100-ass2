package assignments.comp2100.calculator.ExpressionTree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Tree style recursive data structure for representing
 * arithmetic expressions, equipped with methods for
 * incrementally constructing trees from Strings, and evaluating expressions
 */
public abstract class ExpressionTree {
    static final float DELTA = 0.00001f;

    static class Operation {
        Method operation;
        int precedence;
        Operation(Method operation, int precedence) {
            this.operation = operation;
            this.precedence = precedence;
        }
    }

    protected ExpressionTree parent;
    protected int precedence;

    static HashMap<String, Operation> tokenParser = new HashMap<>();
    static {
        try {
            Class[] bArgs = {float.class, float.class};
            Class[] uArg = {float.class};
            tokenParser.put("+", new Operation(OperationDatabase.class.getDeclaredMethod("add", bArgs), 1));
            tokenParser.put("-", new Operation(OperationDatabase.class.getDeclaredMethod("sub", bArgs), 1));
            tokenParser.put("*", new Operation(OperationDatabase.class.getDeclaredMethod("mult", bArgs), 2));
            tokenParser.put("%", new Operation(OperationDatabase.class.getDeclaredMethod("mod", bArgs), 2));
            tokenParser.put("^", new Operation(OperationDatabase.class.getDeclaredMethod("pow", bArgs), 3));
            tokenParser.put("/", new Operation(OperationDatabase.class.getDeclaredMethod("div", bArgs), 2));
            tokenParser.put("(", new Operation(OperationDatabase.class.getDeclaredMethod("identity", uArg), 0));
            tokenParser.put(")", new Operation(OperationDatabase.class.getDeclaredMethod("identity", uArg), 0));
            tokenParser.put("cos", new Operation(OperationDatabase.class.getDeclaredMethod("cos", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("sin", new Operation(OperationDatabase.class.getDeclaredMethod("sin", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("tan", new Operation(OperationDatabase.class.getDeclaredMethod("tan", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("sqrt", new Operation(OperationDatabase.class.getDeclaredMethod("sqrt", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("abs", new Operation(OperationDatabase.class.getDeclaredMethod("abs", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("exp", new Operation(OperationDatabase.class.getDeclaredMethod("exp", uArg), UnaryOperator.UNARY_PRECEDENCE));
            tokenParser.put("log", new Operation(OperationDatabase.class.getDeclaredMethod("log", uArg), UnaryOperator.UNARY_PRECEDENCE));
        } catch (Exception e) {
            System.err.println("Could not initialize tokenParser");
        }
    }

    /**
     * @param input a well-formatted expression, in String form
     * @return true iff the expression is valid (matching brackets, each operator has the correct number of operands)
     */
    public static boolean checkInput(String input) {
        String[] splitInput = input.split(" ");
        boolean scalarIsNeeded = true;
        int bracketsNeeded = 0;
        for (int i=0; i<splitInput.length; i++) {
            if (splitInput[i].equals("")) return false;
            if (!tokenParser.containsKey(splitInput[i])) {
                if (!scalarIsNeeded) {
                    return false;
                }
                scalarIsNeeded = false;
            } else if (splitInput[i].equals("(")) {
                if (!scalarIsNeeded) {
                    return false;
                }
                bracketsNeeded++;
                scalarIsNeeded = true;
            } else if (splitInput[i].equals(")")) {
                if (scalarIsNeeded) {
                    return false;
                }
                bracketsNeeded--;
            } else {
                if (tokenParser.get(splitInput[i]).operation.getParameterTypes().length == 2) {
                    if (scalarIsNeeded) {
                        return false;
                    }
                    scalarIsNeeded = true;
                }
            }
        }
        return bracketsNeeded == 0 && !scalarIsNeeded;
    }

    /**
     * @return true iff this tree contains any Variable nodes
     */
    public boolean isFunction() { return false; }

    /**
     * Converts a well-formatted String into an equivalent ExpressionTree
     * @param expr arithmetic expression in String form, assumed to be valid
     * @return ExpressionTree that will evaluate expr
     */
    public static ExpressionTree parseStringToTree(String expr) {
        String[] input = expr.split(" ");
        ExpressionTree currentTree = ExpressionTree.parseStringToToken(input[0]);
        for (int i=1; i<input.length; i++) {
            currentTree = currentTree.insertExpression(ExpressionTree.parseStringToToken(input[i]));
        }
        return currentTree.getRoot();
    }

    /**
     * Converts a character into a single ExpressionTree node
     * @param token a single token, in String form
     * @return an ExpressionTree with no parents/children
     */
    static ExpressionTree parseStringToToken(String token) {
        if (token.equals("(")) {
            return new LeftBracket();
        } else if (token.equals(")")) {
            return new RightBracket();
        } else if (token.equals("x")) {
            return new Variable();
        } else if (tokenParser.containsKey(token)) {
            Operation op = tokenParser.get(token);
            if (op.operation.getParameterTypes().length == 2) {
                return new BinaryOperator(op);
            } else {
                return new UnaryOperator(op);
            }
        } else {
            return new Scalar(Float.parseFloat(token));
        }
    }

    /**
     * @return the root of the tree
     */
    ExpressionTree getRoot() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRoot();
        }
    }

    /**
     * Inserts the provided ExpressionTree node into
     * this tree, either as a child or parent node
     * depending on the amount of children this
     * node has and the relative precedences of
     * the expressions.
     * @return the current active node
     */
    abstract ExpressionTree insertExpression(ExpressionTree expr);
    public abstract float evaluate();
    public float evaluate(float x) { return evaluate(); }
    public abstract ExpressionTree getDerivative();

    /**
     * @return A simplified version of the expression tree, where redundant nodes have been removed
     */
    public abstract ExpressionTree getSimplified();
    abstract int getPrecedence();
    abstract ExpressionTree getClone();

    void setParent(ExpressionTree node) { parent = node; }
    ExpressionTree getParent() { return parent; }
}
