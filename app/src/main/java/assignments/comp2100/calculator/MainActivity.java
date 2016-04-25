package assignments.comp2100.calculator;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import assignments.comp2100.calculator.ExpressionTree.ExpressionTree;

//        Author: Liyang(Leon) Guan
//        Uni ID: u5684206
//        Declaration: The following code is written all by myself.

public class MainActivity extends Activity {

    // View declarations
    TextView tvDisplay;

    Button bMinus;

    //Variables
    boolean funcFlag = false;
    int rstFlag = 0;
    ArrayList<String> numInputs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the textView
        tvDisplay = (TextView) findViewById(R.id.displayArea);
        tvDisplay.setMovementMethod(new ScrollingMovementMethod());
        tvDisplay.setText("");

        numInputs.add("0");
        numInputs.add("1");
        numInputs.add("2");
        numInputs.add("3");
        numInputs.add("4");
        numInputs.add("5");
        numInputs.add("6");
        numInputs.add("7");
        numInputs.add("8");
        numInputs.add("9");
        numInputs.add(".");

        // input negative sign
        bMinus = (Button) findViewById(R.id.inSubtraction);
        bMinus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvDisplay.setText(tvDisplay.getText().toString() + "-");
                return true;
            }
        });

    }

    /**
     * This method append the symbol of an input button to the String in the textView and scroll the textView to an appropriate position.
     * @param v
     */
    public void append(View v) {
        if (funcFlag) {
            tvDisplay.setText("");
            funcFlag = false;
        }
        int max_lines;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            max_lines = 4;
        } else {
            max_lines = 2;
        }

        if (!(rstFlag == 1 && numInputs.contains(((Button) v).getText().toString()) )) {
            int pre_line_count = tvDisplay.getLineCount();
            tvDisplay.setText(tvDisplay.getText().toString() + ((Button) v).getText().toString());
            rstFlag = 0;
            if (pre_line_count >= max_lines) {
                tvDisplay.scrollTo(0, tvDisplay.getLineHeight() * (tvDisplay.getLineCount() - max_lines));
            }
        }
    }

    /**
     * This method cleans the textView and scroll it to the top.
     * @param v
     */
    public void allClear(View v) {
        tvDisplay.setText("");
        tvDisplay.scrollTo(0, 0);
        rstFlag = 0;
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

        if (tvDisplay.getText().toString().equals("")) {
            rstFlag = 0;
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

        if (ExpressionTree.checkInput(expression)) {
            tvDisplay.scrollTo(0,0);
            try {
                tvDisplay.setText(String.valueOf(ExpressionTree.parseStringToTree(expression).evaluate()));
                rstFlag = 1;
            } catch (NumberFormatException e) {
                // TODO display invalid number error message
            }

        } else {
            new CountDownTimer(3000, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvDisplay.setText("Invalid Expression! \n Disappear in " + millisUntilFinished / 1000 + "s.");
                }

                @Override
                public void onFinish() {
                    allClear(tvDisplay);
                }
            }.start();
        }
    }

    public void drawFunction(View v) {
        funcFlag = true;
        Canvas screen = new Canvas();
        Paint paint = new Paint();
        paint.setARGB(100, 100, 200, 100);
        Bitmap bm = Bitmap.createBitmap(tvDisplay.getWidth(), tvDisplay.getHeight(), Bitmap.Config.RGB_565);
        for (int i=0; i<tvDisplay.getBackground().getBounds().width(); i++) {
            float functionHeight = ExpressionTree.parseStringToTree(tvDisplay.getText().toString()).evaluate();
            if (functionHeight < tvDisplay.getBackground().getBounds().height()) {
                bm.setPixel(tvDisplay.getBackground().getBounds().left + i, (int) (tvDisplay.getHeight() - functionHeight), 500);
                bm.setPixel(tvDisplay.getBackground().getBounds().left + i, (int) (tvDisplay.getHeight() - functionHeight) - 1, 500);
                bm.setPixel(tvDisplay.getBackground().getBounds().left + i, (int) (tvDisplay.getHeight() - functionHeight) + 1, 500);
            }
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(" ", new ImageSpan(this, bm), 0);
        tvDisplay.setText(builder);
    }
}
