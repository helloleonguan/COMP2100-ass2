package assignments.comp2100.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import assignments.comp2100.calculator.ExpressionTree.ExpressionTree;

//        Author: Liyang(Leon) Guan & Nathan F. Ealzar
//        Declaration: The following code is written mainly by Liyang Guan.
//                     The methods written by Nathan F. Ealzar are identified in the comment above methods.

public class MainActivity extends Activity {

    // View declarations
    TextView tvDisplay;
    EditText etEdit;
    Button bVerify;
    LinearLayout llButtonGrid;

    Button bMinus;


    //Variables
    boolean graphFlag = false;
    boolean funcFlag = false;
    boolean evaluateFlag = false;
    boolean resultShownFlag = false;
    int rstFlag = 0;
    ArrayList<String> numInputs = new ArrayList<>();
    String currentExpression;
    MovementMethod scrollingMethod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        etEdit = (EditText) findViewById(R.id.editArea);
        bVerify = (Button) findViewById(R.id.verifyBtn);
        llButtonGrid = (LinearLayout) findViewById(R.id.buttonGrid);

        // setup the textView
        tvDisplay = (TextView) findViewById(R.id.displayArea);
        scrollingMethod = new ScrollingMovementMethod();
        tvDisplay.setMovementMethod(scrollingMethod);

        tvDisplay.setText("");

        // input negative sign
        bMinus = (Button) findViewById(R.id.inSubtraction);
        bMinus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvDisplay.setText(tvDisplay.getText().toString() + "-");
                return true;
            }
        });

        tvDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buffer = tvDisplay.getText().toString();
                tvDisplay.setVisibility(View.GONE);
                etEdit.setVisibility(View.VISIBLE);
                etEdit.setText(buffer);
                etEdit.setSelection(buffer.length());
                etEdit.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etEdit, InputMethodManager.SHOW_IMPLICIT);

                bVerify.setVisibility(View.VISIBLE);
                llButtonGrid.setVisibility(View.INVISIBLE);

            }
        });

    }

    /**
     * This method append the symbol of an input button to the String in the textView and scroll the textView to an appropriate position.
     * @param v
     */
    public void append(View v) {
        if (graphFlag) drawFunction(null);
        resultShownFlag = false;
        int max_lines;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            max_lines = 4;
        } else {
            max_lines = 2;
        }

        if (graphFlag) {
            findViewById(R.id.graphic_display).setVisibility(View.GONE);
            tvDisplay.setVisibility(View.VISIBLE);
            graphFlag = false;
        }

        if (rstFlag == 1 && funcFlag) {
            tvDisplay.setText(currentExpression);
            rstFlag = 0;
            return;
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

    public void appendVar(View v) {
        if (graphFlag) drawFunction(null);
        funcFlag = true;
        append(v);
    }

    /**
     * This method cleans the textView and scroll it to the top.
     * @param v
     */
    public void allClear(View v) {
        if (graphFlag) drawFunction(null);
        tvDisplay.setText("");
        tvDisplay.scrollTo(0, 0);
        rstFlag = 0;
        funcFlag = false;
        evaluateFlag = false;
    }

    /**
     * This method deletes the last input character from the textView and  scroll the textView to an appropriate position.
     * @param v
     */
    public void undo(View v) {
        if (graphFlag) drawFunction(null);
        if (resultShownFlag) return;
        if (!tvDisplay.getText().toString().equals("")) {
            String tmp = tvDisplay.getText().toString();
            if (rstFlag == 1){
                tvDisplay.setText("");
            } else {
                if (tmp.charAt(tmp.length() - 1) == '\n') return;
                tvDisplay.setText(tmp.substring(0,tmp.length() - 1));
            }
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
     * This method is activated by the BACK button. It carries user input string back to the button mode.
     * @param v
     */
    public void back(View v) {
            etEdit.setVisibility(View.GONE);
            bVerify.setVisibility(View.GONE);
            tvDisplay.setVisibility(View.VISIBLE);
            llButtonGrid.setVisibility(View.VISIBLE);
            tvDisplay.setText(etEdit.getText().toString());

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etEdit.getWindowToken(), 0);
    }

    /**
     * This method parses the input expression string and add proper spaces between symbols. If error detected, a toast showing the error message
     * will be displayed on the screen.
     * @param s
     * @return A well-organized expression string waiting to be evaluated.
     */
    public static HashMap parseAndAddSpace(String s) {
        int index;
        HashMap<String, Object> returnValues = new HashMap<>();
        String rst = "";
        int errorType = 0; // 0 - no error, 1 - function typo, 2 - Multiple floating points, 3 - invalid character

        //input operations databases
        ArrayList<String> biSpaceInputs = new ArrayList<>();
        ArrayList<String> rightSpaceInputs = new ArrayList<>();
        ArrayList<String> numInputs = new ArrayList<>();

        biSpaceInputs.add("*");
        biSpaceInputs.add("/");
        biSpaceInputs.add("+");
        biSpaceInputs.add("-");
        biSpaceInputs.add("%");
        biSpaceInputs.add("^");

        rightSpaceInputs.add("exp");
        rightSpaceInputs.add("sqrt");
        rightSpaceInputs.add("abs");
        rightSpaceInputs.add("sinh");
        rightSpaceInputs.add("cosh");
        rightSpaceInputs.add("tanh");
        rightSpaceInputs.add("sin");
        rightSpaceInputs.add("cos");
        rightSpaceInputs.add("tan");
        rightSpaceInputs.add("log");

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
        numInputs.add("x");

        for (index = 0; index < s.length();){
            if (s.charAt(index) == 'x') {
                rst += "x";
                index ++;
            } else if (Character.isLetter(s.charAt(index))){
                int flag = 0;
                for (String function : rightSpaceInputs) {
                    String head = (index + function.length() >= s.length())? s.substring(index) : s.substring(index,index+function.length());
                    if (head.equals(function)) {
                        rst += head + " ";
                        index += head.length();
                        flag++;
                        break;
                    }
                }

                if (flag == 0){
                    //Toast.makeText(this, "Function name typo!", Toast.LENGTH_LONG).show();
                    errorType = 1;
                    rst = null;
                    break;
                }

            } else if (numInputs.contains(Character.toString(s.charAt(index)))){
                int flag = 0;
                int length = 0;
                int point = 0;
                while (flag == 0) {
                    if (index + length < s.length() && s.charAt(index+length) == '.'){
                        point ++;
                        length ++;
                    } else if (index + length < s.length() && numInputs.contains(Character.toString(s.charAt(index + length)))) {
                        length++;
                    } else {
                        flag = 1;
                    }
                }

                if (point >= 2) {
                    //Toast.makeText(this, "Multiple floating points!", Toast.LENGTH_LONG).show();
                    errorType = 2;
                    rst = null;
                    break;
                } else {
                    rst += s.substring(index,index+length);
                    index += length;
                }
            } else if (biSpaceInputs.contains(Character.toString(s.charAt(index)))) {
                if (s.charAt(index) == '-') {
                    int flag = 0;
                    int leftOffset = 1;
                    while(flag == 0) {
                        if (index - leftOffset >=0 && numInputs.contains(Character.toString(s.charAt(index - leftOffset)))) {
                            rst += " - ";
                            index++;
                            flag = 1;
                        } else if (index - leftOffset >=0 && s.charAt(index-leftOffset) == ' '){
                            leftOffset ++;
                        } else {
                            rst += "-";
                            index++;
                            flag = 1;
                        }
                    }
                } else {
                    rst += " " + Character.toString(s.charAt(index)) + " ";
                    index++;
                }
            } else if (s.charAt(index)=='(') {
                rst += "( ";
                index++;
            } else if (s.charAt(index) == ')') {
                rst += " )";
                index++;
            } else if (s.charAt(index) == ' '){
                index++;
            } else {
                //Toast.makeText(this, "Invalid character detected!", Toast.LENGTH_LONG).show();
                errorType = 3;
                rst = null;
                break;
            }

        }
        returnValues.put("str", rst);
        returnValues.put("error", errorType);
        return returnValues;
    }

    /**
     * This method handles the toasts associated with the error detected in the parse method.
     * @param error an int indicating the type o error detected in the parse method.
     */
    public void alertError(int error) {
        switch(error) {
            case 1:
                Toast.makeText(this, "Function name typo!", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Multiple floating points!", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "Invalid character detected!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private ExpressionTree parseExpression(String expr) {
        HashMap parseResult = parseAndAddSpace(expr);
        int error = (int) parseResult.get("error");
        if (error != 0 || expr.equals("")) {
            alertError(error);
            return null;
        }
        String spacedExpr = (String) parseResult.get("str");
        if (!ExpressionTree.checkInput(spacedExpr)) {
            Toast.makeText(this, "Invalid expression, unmatched brackets or operators", Toast.LENGTH_LONG).show();
            return null;
        }
        return ExpressionTree.parseStringToTree(spacedExpr);
    }

    /**
     * This method first checks whether the expression in the textView is legit and then evaluates the expression
     * using a tree parsing algorithm. It returns the result to the textView if the expression is legit. Otherwise,
     * returns an error message.
     * @param v
     */
    public void evaluate(View v) {
        if (resultShownFlag) {
            tvDisplay.setText(currentExpression);
            resultShownFlag = false;
            return;
        }
        if (graphFlag) drawFunction(null);

        String expression = tvDisplay.getText().toString();
        float x = 0;

        int error;
        int lineIndex = expression.indexOf('\n');
        if (lineIndex == -1) {
            error = (int) parseAndAddSpace(expression).get("error");
            currentExpression = (String) parseAndAddSpace(expression).get("str");
        } else {
            error = (int) parseAndAddSpace(expression.substring(0,lineIndex)).get("error");
            currentExpression = (String) parseAndAddSpace(expression).get("str");
        }

        if ( error != 0 || expression.equals("")){
            alertError(error);
            return;
        }
        if (!ExpressionTree.checkInput(expression)) {
            Toast.makeText(this, "Invalid expression, unmatched brackets or operators", Toast.LENGTH_LONG).show();
            return;
        }

        if (funcFlag) {
            if (!evaluateFlag) { // The user entered a function, let them specify the value to evaluate at
                if (rstFlag == 1) {
                    rstFlag = 0;
                    expression = currentExpression;
                }
                tvDisplay.setText(expression + "\n");
                evaluateFlag = true;
                return;
            } else {
                ExpressionTree evaluatePoint = parseExpression(expression.substring(expression.indexOf('\n') + 1));
                if (evaluatePoint == null) return;
                if (evaluatePoint.isFunction()) {
                    Toast.makeText(this, "Point to evaluate at must be constant", Toast.LENGTH_LONG).show();
                    return;
                }

                x = evaluatePoint.evaluate();
                expression = (String) parseAndAddSpace(expression.substring(0, expression.indexOf('\n'))).get("str");
                //tvDisplay.setText(expression);
                currentExpression = expression;
                evaluateFlag = false;
                resultShownFlag = true;
            }
        } else {
            expression = currentExpression;
        }

        tvDisplay.scrollTo(0,0);
        tvDisplay.setText(String.valueOf(ExpressionTree.parseStringToTree((String) parseAndAddSpace(expression).get("str")).evaluate(x)));
        rstFlag = 1;
    }

    /**
     * Written by Nathan F. Ealzar
     * Transforms the current ExpressionTree into one representing its derivative
     */
    public void differentiate(View v) {
        if (evaluateFlag) return;
        if (graphFlag) drawFunction(null);

        ExpressionTree function = parseExpression(tvDisplay.getText().toString());
        if (function != null) {
            tvDisplay.setText(function.getDerivative().getSimplified().toString());
        }
    }

    private static final int WINDOW_SCALE_HORIZONTAL = 6;
    private static final int AXIS_COLOUR = 0xEEEEEE;
    private static final int GRAPH_COLOUR = 0xEE00;
    private static final int LABEL_FONT_SIZE = 20;
    private static final int NOTCH_LENGTH = 5;

    /**
     * Written by Nathan F. Elazar
     * Switches the view to show/hide the graphical display, and
     * plots a graph of the current ExpressionTree over the display.
     */
    public void drawFunction(View v) {
        if (evaluateFlag) return;
        ExpressionTree function = parseExpression(tvDisplay.getText().toString());
        if (function == null) return;

        graphFlag = !graphFlag;
        ((ImageView)findViewById(R.id.graphic_display)).setImageResource(0);
        if (graphFlag) {
            Bitmap bm = Bitmap.createBitmap(tvDisplay.getWidth(), tvDisplay.getHeight(), Bitmap.Config.RGB_565);
            //Draw x and y axis
            for (int i=0; i<tvDisplay.getWidth(); i++) {
                bm.setPixel(i, (int)(tvDisplay.getHeight() / 2.0), AXIS_COLOUR);
            }
            for (int i=0; i<tvDisplay.getHeight(); i++) {
                bm.setPixel((int)(tvDisplay.getWidth() / 2.0), i, AXIS_COLOUR);
            }

            float maxFunctionAbs = 1; //Absolute value of the largest value, used to automatically scale the vertical axis
            float[] functionHeights = new float[tvDisplay.getWidth()];

            //For each pixel in the display, evaluate the function at that point and record the function height
            for (int i = 0; i < tvDisplay.getWidth(); i++) {
                functionHeights[i] = function.evaluate((float) ((i - (tvDisplay.getWidth() / 2.0)) * WINDOW_SCALE_HORIZONTAL / tvDisplay.getWidth()));
                if (functionHeights[i] == Float.POSITIVE_INFINITY || functionHeights[i] == Float.NEGATIVE_INFINITY || Float.isNaN(functionHeights[i])) continue;
                maxFunctionAbs = Math.abs(functionHeights[i]) > maxFunctionAbs ? Math.abs(functionHeights[i]) : maxFunctionAbs;
            }

            maxFunctionAbs = maxFunctionAbs > tvDisplay.getHeight() * 0.5f ? tvDisplay.getHeight() * 0.5f : maxFunctionAbs; //Cap the max value, otherwise plots will be really squished
            int previousPixelHeight = 0;
            boolean previousExists = false;

            //Go through and plot each function height on the bitmap
            for (int i=0; i<tvDisplay.getWidth(); i++) {
                if (functionHeights[i] != Float.POSITIVE_INFINITY && functionHeights[i] != Float.NEGATIVE_INFINITY && !Float.isNaN(functionHeights[i])) {
                    int pixelHeight = (int) (0.5 * tvDisplay.getHeight() * (1 - functionHeights[i] / (maxFunctionAbs * 1.01)));
                    if (!previousExists) {
                        previousPixelHeight = pixelHeight;
                        previousExists = true;
                    }
                    if (pixelHeight >= 0 && pixelHeight < tvDisplay.getHeight()) {
                        if (previousPixelHeight >= 0 && previousPixelHeight < tvDisplay.getHeight()) {
                            for (int j = Math.min(pixelHeight, previousPixelHeight); j <= Math.max(pixelHeight, previousPixelHeight); j++) {
                                bm.setPixel(i, j, GRAPH_COLOUR);
                            }
                        } else {
                            bm.setPixel(i, pixelHeight, GRAPH_COLOUR);
                        }
                    }
                    previousPixelHeight = pixelHeight;
                } else {
                    previousExists = false;
                }
            }

            //Paint the axis labels
            Canvas canvas = new Canvas(bm);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setTextSize(LABEL_FONT_SIZE);
            paint.setShadowLayer(1, 0, 1, Color.WHITE);

            for (int i=-NOTCH_LENGTH; i<NOTCH_LENGTH; i++) {
                bm.setPixel((int)(tvDisplay.getWidth() / 2.0) + i, (int) (0.5 * tvDisplay.getHeight() * (1 - maxFunctionAbs / (maxFunctionAbs * 1.01))), AXIS_COLOUR);
                bm.setPixel(tvDisplay.getWidth() - LABEL_FONT_SIZE, (int)(tvDisplay.getHeight() / 2.0) - i, AXIS_COLOUR);
                bm.setPixel(LABEL_FONT_SIZE, (int)(tvDisplay.getHeight() / 2.0) - i, AXIS_COLOUR);
                bm.setPixel((int)(tvDisplay.getWidth() / 2.0) + i, (int) (-0.5 * tvDisplay.getHeight() * (1 - maxFunctionAbs / (maxFunctionAbs * 1.01)) + tvDisplay.getHeight() - 1), AXIS_COLOUR);
            }
            canvas.drawText(String.valueOf(WINDOW_SCALE_HORIZONTAL / 2), tvDisplay.getWidth() - LABEL_FONT_SIZE, (int) ((tvDisplay.getHeight() / 2.0) - NOTCH_LENGTH), paint);
            canvas.drawText(String.valueOf(-WINDOW_SCALE_HORIZONTAL / 2), LABEL_FONT_SIZE, (int) ((tvDisplay.getHeight() / 2.0) - NOTCH_LENGTH), paint);
            canvas.drawText(String.valueOf(maxFunctionAbs), (int)(tvDisplay.getWidth() / 2.0) + NOTCH_LENGTH, (int) (0.5 * tvDisplay.getHeight() * (1 - maxFunctionAbs / (maxFunctionAbs * 1.01))) + LABEL_FONT_SIZE, paint);
            canvas.drawText(String.valueOf(-maxFunctionAbs), (int)(tvDisplay.getWidth() / 2.0) + NOTCH_LENGTH, (int) (-0.5 * tvDisplay.getHeight() * (1 - maxFunctionAbs / (maxFunctionAbs * 1.01))) - LABEL_FONT_SIZE + tvDisplay.getHeight() - 1, paint);

            tvDisplay.setVisibility(View.GONE);
            findViewById(R.id.graphic_display).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.graphic_display)).setImageBitmap(bm);
        } else {
            findViewById(R.id.graphic_display).setVisibility(View.GONE);
            tvDisplay.setVisibility(View.VISIBLE);
        }
    }
}
