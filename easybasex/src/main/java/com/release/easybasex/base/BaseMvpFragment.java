package com.release.easybasex.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.release.easybasex.utils.ToastUtils;


/**
 * @author Mr.release
 * @create 2019/8/2
 * @Describe
 */
public abstract class BaseMvpFragment<V extends IView, P extends IPresenter<V>> extends BaseFragment implements IView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    public void initView(View view) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.initView(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showError() {
        super.showError();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void loadData(Object data) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void showMsg(String msg) {

    }
}
