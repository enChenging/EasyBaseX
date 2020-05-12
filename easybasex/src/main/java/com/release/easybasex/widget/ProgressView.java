package com.release.easybasex.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.release.easybasex.R;


/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe 进度 圆环
 */
public class ProgressView extends View {
    private Paint paint;
    private int roundColor;
    private float roundWidth;
    private int progressColor; // 圆环进度的颜色
    private float progressWidth;
    private int max;
    private int style;  // 实心或者空心
    private int startAngle; // 进度条起始角度
    public static final int STROKE = 0; // 样式：空心
    public static final int FILL = 1;   // 样式：实心
    private int progress = 30;          // 当前进度


    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.Progress);

        roundColor = mTypedArray.getColor(R.styleable.Progress_srp_roundColor, Color.RED);
        roundWidth = mTypedArray.getDimension(R.styleable.Progress_srp_roundWidth, 5);
        progressColor = mTypedArray.getColor(R.styleable.Progress_srp_progressColor, Color.GREEN);
        progressWidth = mTypedArray.getDimension(R.styleable.Progress_srp_progressWidth, roundWidth);
        max = mTypedArray.getInteger(R.styleable.Progress_srp_max, 100);
        progress = mTypedArray.getInteger(R.styleable.Progress_srp_progress, 30);
        style = mTypedArray.getInt(R.styleable.Progress_srp_style, 0);
        startAngle = mTypedArray.getInt(R.styleable.Progress_srp_startAngle, 90);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int radius = (int) (centerX - roundWidth / 2);

        // step1 画最外层的大圆环
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundColor);
        paint.setAntiAlias(true);
        // 设置画笔样式
        switch (style) {
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
        canvas.drawCircle(centerX, centerX, radius, paint); // 画出圆环

        // step2 画圆弧-画圆环的进度
        paint.setStrokeWidth(progressWidth);
        paint.setColor(progressColor);
        RectF oval = new RectF(centerX - radius , centerX - radius , centerX + radius , centerX + radius ); // 用于定义的圆弧的形状和大小的界限

        int sweepAngle = 360 * progress / max; // 计算进度值在圆环所占的角度
        switch (style) {
            case STROKE:
                // 空心
                canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
                break;
            case FILL:
                // 实心
                canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
                break;
        }
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        postInvalidate();
    }

}
