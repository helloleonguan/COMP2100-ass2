package assignments.comp2100.calculator;

import org.junit.Test;

import assignments.comp2100.calculator.ExpressionTree.Expression;

import static org.junit.Assert.*;

public class ExpressionTreeTest {
    private static final float DELTA = 0.000001f;

    private static final String[] testsAM = {
            "4 + 3 * 5",
            "4 * 3 + 5",
            "4 + 3 * 5 + 2",
            "4 * 3 + 5 * 2"
    };
    private static final float[] ansAM = {
            19,
            17,
            21,
            22
    };

    @Test
    public void additionMultiplicationTest() throws Exception {
        for (int i=0; i<testsAM.length; i++) {
            assertEquals(ansAM[i], Expression.parseStringToTree(testsAM[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsB = {
            "( 4 + 3 ) * 5",
            "4 * ( 3 + 5 )",
            "( 4 + 3 ) * ( 5 + 2 )",
            "4 * ( 3 + 5 ) * 2"
    };
    private static final float[] ansB = {
            35,
            32,
            49,
            64
    };

    @Test
    public void bracketTest() throws Exception {
        for (int i=0; i<testsB.length; i++) {
            assertEquals(ansB[i], Expression.parseStringToTree(testsB[i]).evaluate(), DELTA);
        }
    }
}