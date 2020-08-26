package com.release.simplex.mvp.presenter;

import android.widget.Toast;

import com.release.easybasex.base.BasePresenter;
import com.release.easybasex.utils.AppManager;
import com.release.simplex.MainActivity;
import com.release.simplex.mvp.contract.MainContract;


/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class MainPersenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {


    private long mExitTime = 0;

    @Override
    public void requestData(boolean isRefresh) {
        mView.loadData(null);
//        mView.showLoading();
    }

    @Override
    public void exit(MainActivity context) {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(context, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            AppManager.appExit(context);
        }
    }
}
