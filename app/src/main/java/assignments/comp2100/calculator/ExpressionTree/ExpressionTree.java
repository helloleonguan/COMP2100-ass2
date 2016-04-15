package assignments.comp2100.calculator.ExpressionTree;

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
    protected ExpressionTree parent;
    private static HashMap<String, Class<?>> tokenParser = new HashMap<>();
    static {
        tokenParser.put("+", Addition.class);
        tokenParser.put("-", Subtraction.class);
        tokenParser.put("*", Multiplication.class);
        tokenParser.put("/", Division.class);
        tokenParser.put("(", LeftBracket.class);
        tokenParser.put(")", RightBracket.class);
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
            } else if (tokenParser.get(splitInput[i]).equals(LeftBracket.class)) {
                if (!scalarIsNeeded) {
                    return false;
                }
                bracketsNeeded++;
                scalarIsNeeded = true;
            } else if (tokenParser.get(splitInput[i]).equals(RightBracket.class)) {
                if (scalarIsNeeded) {
                    return false;
                }
                bracketsNeeded--;
            } else {
                try {
                    if (tokenParser.get(splitInput[i]).newInstance() instanceof BinaryOperator) {
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
        try {
            return (ExpressionTree) tokenParser.get(token).newInstance();
        } catch (Exception e) {
            return new Scalar(Float.valueOf(token));
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
     * @return the current active node, which is
     * the node which new expressions should be
     * inserted into
     */
    ExpressionTree getActiveNode() { return this; }

    /**
     * @return the LeftBracket node which
     * contains this node, or else the root
     * if there is no such bracket
     */
    ExpressionTree getScope() {
        if (parent == null) {
            return this;
        } else {
            return parent.getScope();
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

    /**
     * Places the provided ExpressionTree node
     * as this nodes child, ignoring precedences
     * and this nodes number of children
     * @return the current active node
     */
    abstract ExpressionTree appendExpression(ExpressionTree expr);

    public abstract float evaluate();
    public abstract int getPrecedence();

    public void setParent(ExpressionTree node) { parent = node; }
    public ExpressionTree getParent() { return parent; }
}
