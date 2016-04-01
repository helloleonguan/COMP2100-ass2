package assignments.comp2100.calculator.Test_Tree_Parsing;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class Addition extends BinaryOperator {
    private static final int ADDITION_PRIORITY = 1;

    public Addition() {
        super(ADDITION_PRIORITY);
        hasExpression = true;
    }

    public float evaluate() {
        return left.evaluate() + right.evaluate();
    }
}

