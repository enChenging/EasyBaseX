package com.release.easybasex.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.release.easybasex.R;

public class StatusPlaceholderView extends View {

    public StatusPlaceholderView(Context context) {
        this(context, null);
    }

    public StatusPlaceholderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusPlaceholderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);

        int measureHeight = BarUtils.getStatusBarHeight();
        setMeasuredDimension(widthSpec, measureHeight);

    }
}
