package com.daydao.roomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @Author:Han
 * @Create:2017/6/8
 **/

public class RoomView extends View {
    private Paint leftTextPaint, leftbgPaint, rightTextPaint, rightbgPaint, middleLinePaint;
    public int leftTextColor, leftbgColor, rightTextColor, rightbgColor, middleLineColor;
    private Rect leftTextRect = new Rect();
    private float leftTextSize, rightTextSize;
    private int defaultLeftTextColor;
    private int defaultLeftbgColor;
    private int defaultRightTextColor;
    private int defaultRightbgColor;
    private int defaultMiddleLineColor;
    private int mWidth;
    private int mHeight;
    RectF rectF = new RectF(), rectF2 = new RectF();
    Path pathLine = new Path();
    private float defaultLeftTextSize, defaultRightTextSize;
    private int leftTextWidth, righttextWidth, middleLineWidth;
    int maginLeft;
    int maginRight;
    private String leftText;
    private String rightText;


    public RoomView(Context context) {
        this(context, null);
    }

    public RoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoomView, defStyleAttr, 0);
        leftTextColor = a.getColor(R.styleable.RoomView_leftTextColor, defaultLeftTextColor);
        leftbgColor = a.getColor(R.styleable.RoomView_leftbgColor, defaultLeftbgColor);
        rightTextColor = a.getColor(R.styleable.RoomView_rightTextColor, defaultRightTextColor);
        rightbgColor = a.getColor(R.styleable.RoomView_rightbgColor, defaultRightbgColor);
        middleLineColor = a.getColor(R.styleable.RoomView_middleLineColor, defaultMiddleLineColor);
        leftTextSize = a.getDimension(R.styleable.RoomView_leftTextSize, defaultLeftTextSize);
        rightTextSize = a.getDimension(R.styleable.RoomView_leftTextSize, defaultRightTextSize);
        a.recycle();
        initPaint();
    }

    private void init() {
        defaultLeftTextColor = Color.BLACK;
        defaultLeftbgColor = Color.GRAY;
        defaultRightTextColor = Color.WHITE;
        defaultRightbgColor = Color.BLUE;
        defaultMiddleLineColor = Color.GRAY;

        mHeight = dp2px(25);
        defaultLeftTextSize = dp2px(12);
        defaultRightTextSize = dp2px(8);
        leftTextWidth = dp2px(25);
        righttextWidth = dp2px(15);
        middleLineWidth = dp2px(1);
        maginLeft = dp2px(5);
        maginRight = dp2px(5);
    }

    private void initPaint() {
        leftbgPaint = getbgPaint(Paint.Style.FILL, leftbgColor, -1);
        rightbgPaint = getbgPaint(Paint.Style.FILL, rightbgColor, -1);
        middleLinePaint = getbgPaint(Paint.Style.FILL, middleLineColor, middleLineWidth);
        leftTextPaint = getTextPaint(leftTextSize, leftTextColor, Paint.Align.CENTER);
        rightTextPaint = getTextPaint(rightTextSize, rightTextColor, Paint.Align.CENTER);
    }

    private Paint getbgPaint(Paint.Style style, int bgColor, int width) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(bgColor);
        if (width != -1) {
            paint.setStrokeWidth(width);
        }
        return paint;
    }

    private Paint getTextPaint(float textSize, int textColor, Paint.Align align) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    /**
     * 测量宽度
     *
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                if (leftText!=null){
                    float w = 0;
                    final int len = leftText.length();
                    for (int i = 0; i < len; i++) {
                        w += leftTextPaint.measureText(leftText, i, i + 1);
                    }
                    leftTextWidth = (int) w;
                }
                mWidth =  leftTextWidth + righttextWidth + middleLineWidth + maginLeft + maginRight;
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    /**
     * 测量高度
     *
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLeftText(canvas);
        drawRightText(canvas);
        drawMiddleLine(canvas);
    }

    private void drawLeftText(Canvas canvas) {
        rectF.set(0, 0, (getWidth() - righttextWidth), mHeight);
        canvas.drawRoundRect(rectF, 0, 0, leftbgPaint);

        drawText(canvas, leftText);
    }

    private void drawMiddleLine(Canvas canvas) {
        pathLine.moveTo((getWidth() - righttextWidth), mHeight * 3 / 5);
        pathLine.lineTo((getWidth() - righttextWidth) + righttextWidth / 5, mHeight * 7 / 10);
        pathLine.lineTo((getWidth() - righttextWidth), mHeight * 4 / 5);
        canvas.drawPath(pathLine, middleLinePaint);
        pathLine.reset();
    }

    private void drawRightText(Canvas canvas) {
        rectF2.set((getWidth() - righttextWidth), 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF2, 0, 0, rightbgPaint);

        showText(canvas, rightText);
    }


    private void drawText(Canvas canvas, String textString) {
        leftTextRect.left = (int) maginLeft;
        leftTextRect.top = 0;
        leftTextRect.right = (getWidth() - righttextWidth) - maginRight;
        leftTextRect.bottom = mHeight;

        Paint.FontMetricsInt fontMetrics = leftTextPaint.getFontMetricsInt();
        int baseline = (leftTextRect.bottom + leftTextRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, leftTextRect.centerX(), baseline, leftTextPaint);
    }

    private void showText(Canvas canvas, String text) {
        float w;
        final int len = text.length();
        float py = 0;
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            w = rightTextPaint.measureText(text, i, i + 1);//获取字符宽度
            StringBuffer b = new StringBuffer();
            b.append(c);
            if (py > 81) {//定义字的范围
                return;
            }
            if (isChinese(c)) {
                py += (w + 4);
                if (py > 81) {
                    return;
                }
                canvas.drawText(b.toString(), (getWidth() - righttextWidth) + righttextWidth / 2, py, rightTextPaint); //中文处理方法
            } else {
                /*canvas.drawTextOnPath(b.toString(), path, py, -3 - 2, rightbgPaint);//其他文字处理方法
                py += w;*/
            }
        }
    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        defaultLeftTextColor = leftTextColor;
        invalidate();
    }

    public void setLeftbgColor(int leftbgColor) {
        this.leftbgColor = leftbgColor;
        defaultLeftbgColor = leftbgColor;
        invalidate();
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        defaultRightTextColor = rightTextColor;
        invalidate();
    }

    public void setRightbgColor(int rightbgColor) {
        this.rightbgColor = rightbgColor;
        defaultRightbgColor = rightbgColor;
        invalidate();
    }

    public void setMiddleLineColor(int middleLineColor) {
        this.middleLineColor = middleLineColor;
        defaultMiddleLineColor = middleLineColor;
        invalidate();
    }

    public void setLeftTextSize(float leftTextSize) {
        this.leftTextSize = leftTextSize;

        invalidate();
    }

    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = rightTextSize;
        invalidate();
    }


    public void setLeftText(String leftText) {
        this.leftText = leftText;
        invalidate();
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        invalidate();
    }

    public void setMaginLeft(int maginLeft) {
        this.maginLeft = maginLeft;
        invalidate();
    }

    public void setMaginRight(int maginRight) {
        this.maginRight = maginRight;
        invalidate();
    }

    public void notifyColor() {
        initPaint();
    }
}
