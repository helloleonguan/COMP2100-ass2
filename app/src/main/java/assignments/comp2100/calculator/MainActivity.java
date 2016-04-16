package assignments.comp2100.calculator;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import assignments.comp2100.calculator.ExpressionTree.ExpressionTree;

//        Author: Liyang(Leon) Guan
//        Uni ID: u5684206
//        Declaration: The following code is written all by myself.

public class MainActivity extends Activity {

    // View declarations
    TextView tvDisplay;

    Button btAC;
    Button btLeftBracket;
    Button btRightBracket;
    Button btMultiplication;
    Button btSeven;
    Button btEight;
    Button btNine;
    Button btDivision;
    Button btFour;
    Button btFive;
    Button btSix;
    Button btAddition;
    Button btOne;
    Button btTwo;
    Button btThree;
    Button btSubtraction;
    Button btUndo;
    Button btZero;
    Button btDot;
    Button btEqual;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the textView
        tvDisplay = (TextView) findViewById(R.id.displayArea);
        tvDisplay.setMovementMethod(new ScrollingMovementMethod());
        tvDisplay.setText("");

        // linking all buttons
        btAC = (Button) findViewById(R.id.actAllClear);
        btLeftBracket = (Button) findViewById(R.id.inLeftBracket);
        btRightBracket = (Button) findViewById(R.id.inRightBracket);
        btMultiplication = (Button) findViewById(R.id.inMultiplication);
        btSeven = (Button) findViewById(R.id.inSeven);
        btEight = (Button) findViewById(R.id.inEight);
        btNine = (Button) findViewById(R.id.inNine);
        btDivision = (Button) findViewById(R.id.inDivision);
        btFour = (Button) findViewById(R.id.inFour);
        btFive = (Button) findViewById(R.id.inFive);
        btSix = (Button) findViewById(R.id.inSix);
        btAddition = (Button) findViewById(R.id.inAddition);
        btOne = (Button) findViewById(R.id.inOne);
        btTwo = (Button) findViewById(R.id.inTwo);
        btThree = (Button) findViewById(R.id.inThree);
        btSubtraction = (Button) findViewById(R.id.inSubtraction);
        btUndo = (Button) findViewById(R.id.actUndo);
        btZero = (Button) findViewById(R.id.inZero);
        btDot = (Button) findViewById(R.id.inDot);
        btEqual = (Button) findViewById(R.id.actEqual);

    }

    /**
     * This method append the symbol of an input button to the String in the textView and scroll the textView to an appropriate position.
     * @param v
     */
    public void append(View v) {
        int max_lines;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            max_lines = 4;
        } else {
            max_lines = 2;
        }

        int pre_line_count = tvDisplay.getLineCount();
        tvDisplay.setText(tvDisplay.getText().toString() + ((Button) v).getText().toString());
        if (pre_line_count >= max_lines) {
            tvDisplay.scrollTo(0, tvDisplay.getLineHeight() * (tvDisplay.getLineCount() - max_lines));
        }
    }

    /**
     * This method cleans the textView and scroll it to the top.
     * @param v
     */
    public void allClear(View v) {
        tvDisplay.setText("");
        tvDisplay.scrollTo(0,0);
    }

    /**
     * This method deletes the last input character from the textView and  scroll the textView to an appropriate position.
     * @param v
     */
    public void undo(View v) {
        if (!tvDisplay.getText().toString().equals("")) {
            String tmp = tvDisplay.getText().toString();
            tvDisplay.setText(tmp.substring(0, tmp.length() - 1));
        }

        int max_lines;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            max_lines = 4;
        } else {
            max_lines = 2;
        }
        if (tvDisplay.getLineCount() > max_lines) {
            tvDisplay.scrollTo(0, tvDisplay.getLineHeight() * (tvDisplay.getLineCount() - max_lines));
        } else {
            tvDisplay.scrollTo(0,0);
        }
    }

    /**
     * This method first checks whether the expression in the textView is legit and then evaluates the expression
     * using a tree parsing algorithm. It returns the result to the textView if the expression is legit. Otherwise,
     * returns an error message.
     * @param v
     */
    public void evaluate(View v) {
        String expression = tvDisplay.getText().toString();

        // TODO check whether the expression is legit
        if (ExpressionTree.checkInput(expression)) {
            // TODO pass the expression to the parse algorithm and get the result
            tvDisplay.scrollTo(0,0);
            try {
                tvDisplay.setText(String.valueOf(ExpressionTree.parseStringToTree(expression).evaluate()));
            } catch (NumberFormatException e) {
                // TODO display invalid number error message
            }
            /*new CountDownTimer(5000, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvDisplay.setText("Not Available now! \n Disappear in " + millisUntilFinished / 1000 + "s.");
                }

                @Override
                public void onFinish() {
                    allClear(tvDisplay);
                }
            }.start();*/

        } else {
            // TODO display error message
        }

    }

}
