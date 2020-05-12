package com.release.simplex.mvp.contract;


import com.release.easybasex.base.IPresenter;
import com.release.easybasex.base.IView;
import com.release.simplex.MainActivity;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public interface MainContract {

    interface View extends IView {
    }

    interface Presenter extends IPresenter<View> {
        void requestData(boolean isRefresh);

        void exit(MainActivity context);
    }
}
