package assignments.comp2100.calculator.Test_Tree_Parsing;

/**
 * Created by Nathan F. Elazar on 31/03/2016.
 */
public class ScalarNode extends ExpressionTree {
    private float value;

    public ScalarNode(float value) {
        super(-1);
        this.value = value;
        hasExpression = true;
    }

    public float evaluate() {
        return value;
    }
}
