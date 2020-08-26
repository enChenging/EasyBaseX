package com.release.simplex;

import android.content.Context;

import com.release.easybasex.base.BaseApplication;
import com.release.itoolbar.IToolBar;
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

    @Override
    public void initConfig() {
        super.initConfig();
        // QMUISwipeBackActivityManager.init(application);
        IToolBar.init(R.color.theme_color, R.color.white, R.mipmap.toolbar_back_white);
    }
}
