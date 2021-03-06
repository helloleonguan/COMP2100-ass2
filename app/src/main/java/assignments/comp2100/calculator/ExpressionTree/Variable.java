package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 29/04/2016.
 *
 * This class represents the 'x' variable used in functions
 */
public class Variable extends Scalar {
    @Override
    public ExpressionTree getDerivative() { return new Scalar(1); }

    @Override
    public ExpressionTree getClone() { return new Variable(); }

    @Override
    public boolean isFunction() { return true; }

    @Override
    public float evaluate(float x) {
        return x;
    }

    @Override
    public String toString() { return "x"; }
}
