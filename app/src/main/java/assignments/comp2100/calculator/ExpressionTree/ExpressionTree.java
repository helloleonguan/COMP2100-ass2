package assignments.comp2100.calculator.ExpressionTree;

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
    private int precedence;
    private static HashMap<String, Class<?>> tokenParser = new HashMap<>();
    static {
        tokenParser.put("+", Addition.class);
        tokenParser.put("-", Subtraction.class);
        tokenParser.put("*", Multiplication.class);
        tokenParser.put("/", Division.class);
        tokenParser.put("(", LeftBracket.class);
        tokenParser.put(")", RightBracket.class);
    }

    public ExpressionTree(int precedence) {
        this.precedence = precedence;
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
            currentTree = currentTree.addNode(ExpressionTree.parseStringToToken(input[i]));
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
     * Increases the size of the tree by one,
     * by either inserting expr into this tree
     * or inserting this tree into expr
     * @param expr new ExpressionTree node
     * @return an ExpressionTree containing both this and expr
     */
    ExpressionTree addNode(ExpressionTree expr) {
        if (expr.getPrecedence() <= precedence) {
            return expr.insertExpression(this);
        } else {
            return insertExpression(expr);
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
    ExpressionTree getActiveNode() {
        return this;
    }

    /**
     * @return the LeftBracket node which
     * contains this node, or else root
     * if there is no such bracket
     */
    ExpressionTree getScope() {
        if (parent == null) {
            return this;
        } else {
            return parent.getScope();
        }
    }

    ExpressionTree getContext(int precedence) {
        if (parent != null) {
            return this.precedence <= precedence
                    ? this
                    : parent.getContext(precedence);
        } else {
            return this;
        }
    }

    /**
     * Places expr as one of this nodes children,
     * and adjusts the tree appropriately to maintain
     * proper ordering of expressions.
     * @return the current active node
     */
    abstract ExpressionTree insertExpression(ExpressionTree expr);

    public abstract float evaluate();

    public int getPrecedence() { return precedence; }
    public void setParent(ExpressionTree node) { parent = node; }
    public ExpressionTree getParent() { return parent; }
}
