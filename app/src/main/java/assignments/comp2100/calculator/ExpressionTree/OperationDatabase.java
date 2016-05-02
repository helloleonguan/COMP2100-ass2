package assignments.comp2100.calculator.ExpressionTree;

import java.util.HashMap;

/**
 * Created by Nathan F. Elazar on 23/04/2016.
 */
public abstract class OperationDatabase {
    static float add(float x, float y) { return x + y; }
    static float sub(float x, float y) { return x - y; }
    static float mult(float x, float y) { return x * y; }
    static float div(float x, float y) { return x / y; }
    static float mod(float x, float y) { return x % y; }
    static float pow(float x, float y) { return (float) Math.pow(x, y); }
    static float identity(float x) { return x; }
    static float abs(float x) { return Math.abs(x); }
    static float sin(float x) { return (float) Math.sin(Math.toRadians(x)); }
    static float cos(float x) { return (float) Math.cos(Math.toRadians(x)); }
    static float tan(float x) { return (float) Math.tan(Math.toRadians(x)); }
    static float sqrt(float x) { return (float) Math.sqrt(x); }
    static float exp(float x) { return (float) Math.exp(x); }
    static float log(float x) { return (float) Math.log(x); }

    static HashMap<String, String> reverseTokenizer = new HashMap<>();
    static {
        try {
            reverseTokenizer.put("add", " + ");
            reverseTokenizer.put("sub", "-");
            reverseTokenizer.put("mult", "*");
            reverseTokenizer.put("mod", "%");
            reverseTokenizer.put("pow", "^");
            reverseTokenizer.put("div", "/");
            reverseTokenizer.put("cos", "cos");
            reverseTokenizer.put("sin", "sin");
            reverseTokenizer.put("tan", "tan");
            reverseTokenizer.put("sqrt", "sqrt");
            reverseTokenizer.put("abs", "abs");
            reverseTokenizer.put("exp", "exp");
            reverseTokenizer.put("log", "log");
        } catch (Exception e) {
            System.err.println("Could not initialize tokenParser");
        }
    }
}
