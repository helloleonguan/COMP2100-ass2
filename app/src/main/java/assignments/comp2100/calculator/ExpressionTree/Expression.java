package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public abstract class Expression {
    protected Expression parent;
    private int parentPriority;
    private int priority;
    protected boolean hasExpression = false;

    public Expression(int priority) {
        this.priority = priority;
    }

    public static Expression parseStringToTree(String expr) {
        String[] input = expr.split(" ");
        Expression currentTree = Expression.parseStringToToken(input[0]);
        for (int i=1; i<input.length; i++) {
            currentTree = currentTree.addNode(Expression.parseStringToToken(input[i]));
        }
        return currentTree.getRoot();
    }

    public static Expression parseStringToToken(String expression) {
        if (expression.equals("+")) {
            return new Addition();
        } else if (expression.equals("*")) {
            return new Multiplication();
        } else if (expression.equals("(")) {
            return new LeftBracket();
        } else if (expression.equals(")")) {
            return new RightBracket();
        } else {
            return new Scalar(Float.valueOf(expression));
        }
    }

    public Expression addNode(Expression expr) {
        if (expr.getPriority() < priority) {
            return expr.insertExpression(this);
        } else {
            return insertExpression(expr);
        }
    }

    public Expression getRoot() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRoot();
        }
    }

    public Expression getNode() {
        return this;
    }

    public Expression getContext() {
        if (parent == null) {
            return this;
        } else {
            return parent.getContext();
        }
    }

    public abstract Expression insertExpression(Expression expr);
    public abstract float evaluate();

    public int getPriority() { return priority; }
    public void setParent(Expression node) { parent = node; }
    public Expression getParent() { return parent; }
}
