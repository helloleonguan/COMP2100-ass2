package assignments.comp2100.calculator;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

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

        tvDisplay = (TextView) findViewById(R.id.displayArea);
        tvDisplay.setMovementMethod(new ScrollingMovementMethod());
        tvDisplay.setText("");

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

    public void allClear(View v) {
        tvDisplay.setText("");
    }

    public void undo(View v) {
        if (!tvDisplay.getText().toString().equals("")) {
            String tmp = tvDisplay.getText().toString();
            tvDisplay.setText(tmp.substring(0, tmp.length() - 1));
        }
    }

    public void evaluate(View v) {
        String expression = tvDisplay.getText().toString();

        // TODO check whether the expression is legit
        if (true) {
            // TODO pass the expression to the parse algorithm and get the result
            tvDisplay.setText("Not available now.");
        } else {
            // TODO display error message
        }

    }

}
