package assignments.comp2100.calculator;

import org.junit.Test;

import assignments.comp2100.calculator.ExpressionTree.ExpressionTree;

import static org.junit.Assert.*;

public class ExpressionTreeTest {
    private static final float DELTA = 0.0001f;

    private static final String[] testsAM = {
            "4 + 3 * 5",
            "4 * 3 + 5",
            "4 + 3 * 5 + 2",
            "4 * 3 + 5 * 2",
            "3 - 2 - 4 / 2 * 5 + 6 * 8",
            "4 / 8 * 2 / 4 - 6",
            "6 / 3 / 2",
            "2 ^ 3",
            "2 ^ 2 ^ 2",
            "3 + 2 * 4 ^ 2",
            "3 ^ 2 * 4 + 2",
    };
    private static final float[] ansAM = {
            19,
            17,
            21,
            22,
            39,
            -5.75f,
            1,
            8,
            16,
            35,
            38
    };

    @Test
    public void additionMultiplicationTest() throws Exception {
        for (int i=0; i<testsAM.length; i++) {
            assertEquals("testAM " + i + " failed", ansAM[i], ExpressionTree.parseStringToTree(testsAM[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsB = {
            "( 4 + 3 ) * 5",
            "4 * ( 3 + 5 )",
            "( 4 + 3 ) * ( 5 + 2 )",
            "4 * ( 3 + 5 ) * 2",
            "4 / ( 2 + 4 ) * 3",
            "2 * ( 4 + 3 + 5 ) + 1",
            "( ( 2 + 3 ) * 4 ) + 2"
    };
    private static final float[] ansB = {
            35,
            32,
            49,
            64,
            2,
            25,
            22
    };

    @Test
    public void bracketTest() throws Exception {
        for (int i=0; i<testsB.length; i++) {
            assertEquals("testB " + i + " failed", ansB[i], ExpressionTree.parseStringToTree(testsB[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsF = {
            "log " + Math.E,
            "2 * log ( 2 * " + Math.E + " )",
            "exp 2 + 8 * 2",
            "exp ( 2 + 4 ) * 2",
            "exp log 5",
            "exp ( log " + Math.E + " + 2 ) * 2"
    };
    private static final float[] ansF = {
            1,
            (float) (2 * Math.log(2 * Math.E)),
            (float) (Math.exp(2) + 16),
            (float) (Math.exp(6) * 2),
            5,
            (float) (Math.exp(Math.log(Math.E) + 2) * 2)
    };

    @Test
    public void functionTest() throws Exception {
        for (int i=0; i<testsF.length; i++) {
            assertEquals("testF " + i + " failed", ansF[i], ExpressionTree.parseStringToTree(testsF[i]).evaluate(), DELTA);
        }
    }

    private static final String[] testsIC = {
            "++",
            "()",
            "1+(2+2))",
            "1+2",
            "1*(2*3)"
    };

    private static final boolean[] ansIC = {
            false,
            false,
            false,
            true,
            true
    };

    @Test
    public void cleanInputTest() throws Exception {
        for (int i=0; i<testsIC.length; i++) {
            assertEquals("testIC " + i + " failed", ansIC[i], ExpressionTree.checkInput((String)MainActivity.parseAndAddSpace(testsIC[i]).get("str")));
        }
    }

    private static final String[] testsDX = {
            "2 * x",
            "x * x",
            "exp x",
            "log x",
            "exp cos x",
            "( cos x ) * tan x",
            "x ^ 3",
            "x ^ x",
            "2 ^ x"
    };

    @Test
    public void differentiationTest() throws Exception {
        for (int i=0; i<testsDX.length; i++) {
            System.out.println(ExpressionTree.parseStringToTree(testsDX[i]).getDerivative().getSimplified().toString());
        }
    }
}