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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        super.showLoading();
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
