package assignments.comp2100.calculator.ExpressionTree;

/**
 * Created by Nathan F. Elazar on 23/04/2016.
 */
public abstract class OperationDatabase {
    static float add(float x, float y) { return x + y; }
    static float sub(float x, float y) { return x - y; }
    static float mult(float x, float y) { return x * y; }
    static float div(float x, float y) { return x / y; }
    static float mod(float x, float y) { return x % y; }
    static float identity(float x) { return x; }
    static float abs(float x) { return (float) Math.abs(x); }
    static float sin(float x) { return (float) Math.sin(Math.toRadians(x)); }
    static float cos(float x) { return (float) Math.cos(Math.toRadians(x)); }
    static float tan(float x) { return (float) Math.tan(Math.toRadians(x)); }
    static float sinh(float x) { return (float) Math.sinh(Math.toRadians(x)); }
    static float cosh(float x) { return (float) Math.cosh(Math.toRadians(x)); }
    static float tanh(float x) { return (float) Math.tanh(Math.toRadians(x)); }
    static float sqrt(float x) { return (float) Math.sqrt(x); }
    static float exp(float x) { return (float) Math.exp(x); }
    static float log(float x) { return (float) Math.log(x); }
}
