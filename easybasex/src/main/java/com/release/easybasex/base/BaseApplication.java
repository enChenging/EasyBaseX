package com.release.easybasex.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.release.easybasex.utils.AppManager;
import com.release.easybasex.utils.AppUtils;

/**
 * @author Mr.release
 * @create 2019/5/14
 * @Describe
 */
public abstract class BaseApplication extends Application {

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
