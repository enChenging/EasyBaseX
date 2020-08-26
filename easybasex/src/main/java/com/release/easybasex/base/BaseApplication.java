package com.release.easybasex.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.release.easybasex.BuildConfig;
import com.release.easybasex.utils.AppManager;
import com.release.easybasex.utils.AppUtils;
import com.release.easybasex.utils.CrashHandler;
import com.release.easybasex.utils.SPUtil;

/**
 * @author Mr.release
 * @create 2019/5/14
 * @Describe
 */
public abstract class BaseApplication<T> extends Application {

    private static BaseApplication sInstance;

    public static boolean mIsForeground = false;

    public static boolean mIsBackToForeground = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        setApplication(this);
        SPUtil.getInstance(this);
        initConfig();
    }

    public void initConfig() {
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

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = (BaseApplication) application;
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            private int refCount = 0;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (refCount == 0) {
                    mIsBackToForeground = true;
                } else {
                    mIsBackToForeground = false;
                }
                refCount++;
                mIsForeground = true;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                refCount--;
                if (refCount == 0) {
                    mIsForeground = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.removeActivity(activity);
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static BaseApplication getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    /**
     * token过期
     */
    public void tokenExpire() {

    }
}
