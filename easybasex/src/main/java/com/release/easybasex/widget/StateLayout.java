package com.release.easybasex.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.release.easybasex.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class StateLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_SHOW = 1002;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnRetryListener mOnRetryListener;
    private int mEmptyStatus = 0;
    private int mBgColor;
    private static int mLoadingThemeColor;
    private static Style mStyle;
    private String msg;

    TextView mTvEmptyMessage;
    View mRlEmptyContainer;
    SpinKitView mEmptyLoading;
    FrameLayout mEmptyLayout;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public static void initLoadingStyle(int loadingThemeColor, Style style) {
        mLoadingThemeColor = loadingThemeColor;
        mStyle = style;
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.cyc_layout_state_view, this);
        mEmptyLayout = findViewById(R.id.fl_empty_layout);
        mRlEmptyContainer = findViewById(R.id.rl_empty_container);
        mTvEmptyMessage = findViewById(R.id.tv_no_data);
        mEmptyLoading = findViewById(R.id.empty_loading);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();

        mTvEmptyMessage.setOnClickListener(v -> {
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry();
            }
        });

        if (mLoadingThemeColor != 0) {
            mEmptyLoading.setColor(ContextCompat.getColor(mContext, mLoadingThemeColor));
        }
        if (mStyle != null) {
            mEmptyLoading.setIndeterminateDrawable(SpriteFactory.create(mStyle));
        }
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 显示视图
     */
    public void show() {
        mEmptyStatus = STATUS_SHOW;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setStateMessage(String msg) {
        this.msg = msg;
        mTvEmptyMessage.setText(msg);
    }


    /**
     * 设置图片
     *
     * @param drawable
     */
    public void setStateIcon(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        mTvEmptyMessage.setCompoundDrawables(null, drawable, null, null);
    }


    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setStateMessage(mContext.getResources().getString(R.string.no_data));
                setStateIcon(mContext.getResources().getDrawable(R.mipmap.ic_empty));
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_ERROR:
                setStateMessage(mContext.getResources().getString(R.string.data_error));
                setStateIcon(mContext.getResources().getDrawable(R.mipmap.ic_error));
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
            case STATUS_SHOW:
                setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_ERROR, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}