package assignments.comp2100.calculator.Test_Tree_Parsing;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class ExpressionTree {
    protected ExpressionTree parent;
    protected ExpressionTree left;
    protected ExpressionTree right;
    private int parentPriority;
    private int priority;
    protected boolean hasExpression = false;

    public ExpressionTree(int priority) {
        this.priority = priority;
    }

    public ExpressionTree parseString(String expression) {
        if (expression.equals("+")) {
            return new Addition();
        } else if (expression.equals("*")) {
            return new Multiplication();
        } else {
            return new ScalarNode(Float.valueOf(expression));
        }
    }

    public ExpressionTree addNode(String expression) {
        ExpressionTree node = parseString(expression);
        if (node instanceof ScalarNode) {
            return addScalar(node);
        } else {
            return addOperator(node);
        }
    }

    public ExpressionTree addOperator(ExpressionTree node) {
        if (!hasExpression) {
            node.left = left;
            node.right = right;
            return node;
        }
        if (node.getPriority() <= parentPriority) {
            if (parent == null) {
                parent = new BinaryOperator(node.getPriority());
            }
            parent.setRight(node);
            node.setParent(parent);
            return parent;
        } else {
            ExpressionTree temp = right;
            right = node;
            right.setLeft(temp);
            right.setParent(this);
            return right;
        }
    }

    public ExpressionTree getAncestor() {
        if (parent == null) {
            return this;
        } else {
            return parent;
        }
    }

    public ExpressionTree addScalar(ExpressionTree node) {
        if (left == null) {
            left = node;
        } else {
            right = node;
        }
        node.setParent(this);
        return this;
    }

    public float evaluate() {
        System.out.println("u wot m9?");
        return 0;
    }

    public int getPriority() { return priority; }
    public void setRight(ExpressionTree node) { right = node; }
    public void setLeft(ExpressionTree node) { left = node; }
    public void setParent(ExpressionTree node) { parent = node; }
}
