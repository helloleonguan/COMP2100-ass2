package assignments.comp2100.calculator.ExpressionTree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 *
 * Tree style recursive data structure, equipped
 * with methods for incrementally constructing
 * trees from Strings, and evaluating expressions
 */
public abstract class ExpressionTree {
    public static class Operation {
        Method operation;
        int precedence;

        Operation(Method operation, int precedence) {
            this.operation = operation;
            this.precedence = precedence;
        }
    }
    protected ExpressionTree parent;
    private static HashMap<String, Operation> tokenParser = new HashMap<>();
    static {
        try {
            Class[] bArgs = {float.class, float.class};
            Class[] uArg = {float.class};
            tokenParser.put("+", new Operation(OperationDatabase.class.getDeclaredMethod("add", bArgs), 1));
            tokenParser.put("-", new Operation(OperationDatabase.class.getDeclaredMethod("sub", bArgs), 1));
            tokenParser.put("*", new Operation(OperationDatabase.class.getDeclaredMethod("mult", bArgs), 2));
            tokenParser.put("/", new Operation(OperationDatabase.class.getDeclaredMethod("div", bArgs), 2));
            tokenParser.put("(", new Operation(OperationDatabase.class.getDeclaredMethod("identity", uArg), 0));
            tokenParser.put(")", new Operation(OperationDatabase.class.getDeclaredMethod("identity", uArg), 0));
            tokenParser.put("exp", new Operation(OperationDatabase.class.getDeclaredMethod("exp", uArg), Integer.MAX_VALUE));
            tokenParser.put("log", new Operation(OperationDatabase.class.getDeclaredMethod("log", uArg), Integer.MAX_VALUE));
        } catch (Exception e) {
            System.err.println("could not initialize");
        }
    }

    public static boolean checkInput(String input) throws IllegalArgumentException {
        String[] splitInput = input.split(" ");
        boolean scalarIsNeeded = true;
        int bracketsNeeded = 0;
        for (int i=0; i<splitInput.length; i++) {
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
                try {
                    if (tokenParser.get(splitInput[i]).operation.getParameterTypes().length == 2) {
                        if (scalarIsNeeded) {
                            return false;
                        }
                        scalarIsNeeded = true;
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return bracketsNeeded == 0 && !scalarIsNeeded;
    }

    /**
     * Converts a String into an equivalent ExpressionTree
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
     * Converts a character into a single ExpressionTree
     * @param token a single token, in String form
     * @return an ExpressionTree with no parents/children
     */
    public static ExpressionTree parseStringToToken(String token) {
        if (token.equals("(")) {
            return new LeftBracket();
        } else if (token.equals(")")) {
            return new RightBracket();
        } else if (tokenParser.containsKey(token)) {
            Operation op = tokenParser.get(token);
            if (op.operation.getParameterTypes().length == 2) {
                return new BinaryOperator(op.operation, op.precedence);
            } else {
                return new UnaryOperator(op.operation, op.precedence);
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
    abstract int getPrecedence();

    void setParent(ExpressionTree node) { parent = node; }
    ExpressionTree getParent() { return parent; }
}