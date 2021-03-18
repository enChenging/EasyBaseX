package com.release.easybasex.base;

import android.os.Bundle;


/**
 * @author Mr.release
 * @create 2019/8/2
 * @Describe
 */
public abstract class BaseMvpActivity<V extends IView, P extends IPresenter<V>> extends BaseActivity implements IView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void showNoData() {
        super.showNoData();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void showError() {
        super.showError();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void showMsg(String msg) {

    }
}
