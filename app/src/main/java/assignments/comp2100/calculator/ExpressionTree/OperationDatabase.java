package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 23/04/2016.
 */
public abstract class OperationDatabase {
    static float add(float x, float y) { return x + y; }
    static float sub(float x, float y) { return x - y; }
    static float mult(float x, float y) { return x * y; }
    static float div(float x, float y) { return x / y; }
    static float identity(float x) { return x; }
    static float exp(float x) { return (float) Math.exp(x); }
    static float log(float x) { return (float) Math.log(x); }
}
