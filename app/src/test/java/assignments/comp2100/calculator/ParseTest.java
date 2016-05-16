package assignments.comp2100.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

//        Author: Liyang(Leon) Guan
//        Declaration: The following code is written mainly by Liyang Guan.

public class ParseTest {
    @Test
    public void parseTest() {
        assertTrue(MainActivity.parseAndAddSpace("lig 9").get("str") == null);
        assertTrue(MainActivity.parseAndAddSpace("xbs 9").get("str") == null);
        assertTrue(MainActivity.parseAndAddSpace("abs 9..98").get("str") == null);
        assertTrue(MainActivity.parseAndAddSpace("abs#;9").get("str") == null);
        assertTrue(MainActivity.parseAndAddSpace("").get("str").equals(""));
        assertTrue(MainActivity.parseAndAddSpace("(9%4+1)").get("str").equals("( 9 % 4 + 1 )"));
        assertTrue(MainActivity.parseAndAddSpace("x/9").get("str").equals("x / 9"));
        assertTrue(MainActivity.parseAndAddSpace("-9+9").get("str").equals("-9 + 9"));
        assertTrue(MainActivity.parseAndAddSpace("abs cos9").get("str").equals("abs cos 9"));
        assertTrue(MainActivity.parseAndAddSpace("(abs-9-1) / sin9").get("str").equals("( abs -9 - 1 ) / sin 9"));
    }
}
