package com.release.simplex;

import android.content.Context;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.release.easybasex.base.BaseApplication;
import com.release.easybasex.utils.CrashHandler;
import com.release.easybasex.utils.SPUtil;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class App extends BaseApplication {

    private static App mContext;

    public static App getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initConfig();
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //指定为经典Header，默认是 贝塞尔雷达Header
                return new TaurusHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                //指定为经典Footer，默认是 BallPulseFooter
                //new ClassicsFooter(context).setDrawableSize(20);
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
    }

    private void initConfig() {
//        QMUISwipeBackActivityManager.init(application);
        SPUtil.getInstance(this);

        if (BuildConfig.DEBUG)
            CrashHandler.getInstance().init(this);

        PrettyFormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("cyc")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
