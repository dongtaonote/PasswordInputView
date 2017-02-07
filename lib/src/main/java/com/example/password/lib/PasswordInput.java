package com.example.password.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

public class PasswordInput extends EditText {

    @ColorInt
    private int borderNotFocusedColor; //边框未选中时的颜色
    @ColorInt
    private int borderFocusedColor; //边框选中时的颜色
    @ColorInt
    private int borderContentColor; //边框未选中时的颜色
    @ColorInt
    private int textColor; //圆点未选中时的颜色
    @ColorInt
    private int backgroundColor; //背景色
    @ColorInt
    private int cursorColor;
    private int cursorWidth;
    private int textSize;
    private int borderWidth; //边框宽度
    private Paint mBorderPaint;
    private Paint mBorderContentPaint;
    private Paint mDotPaint; //圆点画笔
    private Paint mBackgroundPaint; //背景画笔
    private Paint mCursorPaint;
    private int passwordLength; //字符方块的数量
    private float boxMarge; //字符方块的marge
    private float boxRadius; //字符方块的边角圆弧
    private int currTextLen = 0; //现在输入Text长度
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private boolean cursorVisible = true;//是否显示光标
    private boolean cipherText = false;//是否展示密文
    private float dotRadius; //圆点半径

    public PasswordInput(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrVar(context, attrs);
        initPaint();
        initView();
    }

    private void initAttrVar(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PasswordInput);
        borderNotFocusedColor = ta.getColor(R.styleable.PasswordInput_bordNotFocusedColor, Color.parseColor("#fafafa"));
        borderFocusedColor = ta.getColor(R.styleable.PasswordInput_bordFocusedColor, Color.parseColor("#3462FF"));
        borderContentColor = ta.getColor(R.styleable.PasswordInput_bordContentColor, Color.parseColor("#f5f5f5"));
        textColor = ta.getColor(R.styleable.PasswordInput_textColor, Color.parseColor("#3c3c3c"));
        backgroundColor = ta.getColor(R.styleable.PasswordInput_backgroundColor, Color.WHITE);
        cursorColor = ta.getColor(R.styleable.PasswordInput_cursorColor, Color.parseColor("#565656"));
        cursorWidth = ta.getInt(R.styleable.PasswordInput_cursorWidth, UIUtils.getPixels(getContext(), 1));
        textSize = ta.getInt(R.styleable.PasswordInput_textSize, UIUtils.getPixels(getContext(), 25));
        borderWidth = ta.getInt(R.styleable.PasswordInput_bordWidth, UIUtils.getPixels(getContext(), 1));
        passwordLength = ta.getInt(R.styleable.PasswordInput_passwordLength, 6);
        boxMarge = ta.getInt(R.styleable.PasswordInput_boxMarge, UIUtils.getPixels(getContext(), 3));
        boxRadius = ta.getInt(R.styleable.PasswordInput_boxRadius, UIUtils.getPixels(getContext(), 3));
        dotRadius = ta.getDimension(R.styleable.PasswordInput_dotRadius, UIUtils.getPixels(context, 11));
        cursorVisible = ta.getBoolean(R.styleable.PasswordInput_cursorVisible, true);
        cipherText = ta.getBoolean(R.styleable.PasswordInput_cipherText, false);
        ta.recycle();
    }

    private void initPaint() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStrokeWidth(borderWidth);
        mBorderPaint.setColor(borderNotFocusedColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mBorderContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderContentPaint.setColor(borderContentColor);
        mBorderContentPaint.setStyle(Paint.Style.FILL);

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setColor(textColor);
        mDotPaint.setTextSize(textSize);
        mDotPaint.setStyle(Paint.Style.FILL);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mCursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCursorPaint.setStrokeWidth(cursorWidth);
        mCursorPaint.setColor(cursorColor);
        mCursorPaint.setStyle(Paint.Style.FILL);
    }

    private void initView() {
        setCursorVisible(cursorVisible);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setMaxLen(passwordLength);
    }

    private void setMaxLen(int maxLength) {
        if (maxLength >= 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            setFilters(NO_FILTERS);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        canvas.save();
        drawBackGround(canvas, height, width);
        drawBorder(canvas, height, width);
        drawDot(canvas, height, width);
        drawCursor(canvas, height, width);
        canvas.restore();
    }

    private void drawCursor(Canvas canvas, int height, int width) {
        if (currTextLen < passwordLength) {
            float fx, fy = height / 2;
            float tx, ty = height / 2;
            float half = width / passwordLength / 2;
            fx = width / passwordLength * currTextLen + half / 5 * 3;
            tx = width / passwordLength * currTextLen + (half / 5 * 7);
            canvas.drawLine(fx, fy, tx, ty, mCursorPaint);
        }
    }

    private void drawBackGround(Canvas canvas, int height, int width) {
        canvas.drawRect(0, 0, width, height, mBackgroundPaint);
    }

    private void drawBorder(Canvas canvas, int height, int width) {
        for (int i = 0; i < passwordLength; i++) {
            RectF rect = generationSquareBoxRectF(height, width, i);
            if (i == currTextLen && isFocusable()) {
                mBorderPaint.setColor(borderFocusedColor);
            } else {
                mBorderPaint.setColor(borderNotFocusedColor);
            }
            RectF rect_t = generationSquareBoxRectF(height, width, i);
            rect_t.left += 2;
            rect_t.top += 2;
            rect_t.right -= 2;
            rect_t.bottom -= 2;
            canvas.drawRoundRect(rect_t, boxRadius, boxRadius, mBorderContentPaint);
            canvas.drawRoundRect(rect, boxRadius, boxRadius, mBorderPaint);
        }
    }

    private void drawDot(Canvas canvas, int height, int width) {
        float cx;
        float half = width / passwordLength / 2;
        if (cipherText) {
            float cy = height / 2;
            for (int i = 0; i < currTextLen; i++) {
                cx = width * i / passwordLength + half;
                canvas.drawCircle(cx, cy, dotRadius, mDotPaint);
            }
        } else {
            float cy = height / 10 * 7;
            for (int i = 0; i < currTextLen; i++) {
                cx = width * i / passwordLength + half / 10 * 7;
                canvas.drawText(getText().toString(), i, i + 1, cx, cy, mDotPaint);
            }
        }
    }

    private RectF generationSquareBoxRectF(int height, int width, int i) {
        float boxWidth = (width / passwordLength);
        float boxHeight = height;
        float left = boxMarge + boxWidth * i;
        float right = boxWidth * (i + 1) - boxMarge;
        float top = boxMarge;
        float bottom = boxHeight - boxMarge;

        float min = Math.min(boxWidth, boxHeight);

        float dw = (boxWidth - min) / 2F;
        float dh = (boxHeight - min) / 2F;
        left += dw;
        right -= dw;
        top += dh;
        bottom -= dh;
        return new RectF(left, top, right, bottom);
    }


    /**
     * 设置是否展示明文
     * 默认是明文
     */
    public void setClipherText(boolean cipherText) {
        this.cipherText = cipherText;
    }

    /**
     * 设置光标是否展示
     * 这里有个bug
     * 如果光标不展示，长按就没有系统的复制粘贴
     */
    public void setCursorVisiable(boolean cursorVisible) {
        this.cursorVisible = cursorVisible;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.currTextLen = text.toString().length();
        if (null != textLenChangeListen) {
            textLenChangeListen.onTextLenChange(text, currTextLen);
        }
    }

    public interface TextLenChangeListen {
        void onTextLenChange(CharSequence text, int len);
    }

    private TextLenChangeListen textLenChangeListen;

    public void setTextLenChangeListen(TextLenChangeListen lenListen) {
        textLenChangeListen = lenListen;
    }
}
