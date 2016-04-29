package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 29/04/2016.
 */
public class Variable extends Scalar {
    @Override
    public float evaluate(float x) {
        return x;
    }
}
