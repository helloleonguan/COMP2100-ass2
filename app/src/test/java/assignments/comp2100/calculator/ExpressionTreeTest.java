package assignments.comp2100.calculator;

import org.junit.Test;

import assignments.comp2100.calculator.ExpressionTree.ExpressionTree;

import static org.junit.Assert.*;

public class ExpressionTreeTest {
    private static final float DELTA = 0.000001f;

    private static final String[] testsAM = {
            "4 + 3 * 5",
            "4 * 3 + 5",
            "4 + 3 * 5 + 2",
            "4 * 3 + 5 * 2",
            "3 - 2 - 4 / 2 * 5 + 6 * 8",
            "4 / 8 * 2 / 4 - 6"
    };
    private static final float[] ansAM = {
            19,
            17,
            21,
            22,
            39,
            -5.75f
    };

    @Test
    public void additionMultiplicationTest() throws Exception {
        for (int i=0; i<testsAM.length; i++) {
            assertEquals(ansAM[i], ExpressionTree.parseStringToTree(testsAM[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsB = {
            "( 4 + 3 ) * 5",
            "4 * ( 3 + 5 )",
            "( 4 + 3 ) * ( 5 + 2 )",
            "4 * ( 3 + 5 ) * 2",
            "4 / ( 2 + 4 ) * 3"
    };
    private static final float[] ansB = {
            35,
            32,
            49,
            64,
            2
    };

    @Test
    public void bracketTest() throws Exception {
        for (int i=0; i<testsB.length; i++) {
            assertEquals(ansB[i], ExpressionTree.parseStringToTree(testsB[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsIC = {
            "+ +",
            "1 1",
            "( )",
            "1 + ( 2 + 2 ) )",
            "1 + 2",
            "1 * ( 2 * 3 )"
    };

    private static final boolean[] ansIC = {
            false,
            false,
            false,
            false,
            true,
            true
    };

    @Test
    public void cleanInputTest() throws Exception {
        for (int i=0; i<testsIC.length; i++) {
            assertEquals("testIC " + i + " failed", ansIC[i], ExpressionTree.checkInput(testsIC[i]));
        }
    }
}