package com.release.easybasex.base;

import android.os.Bundle;

import com.release.easybasex.R;
import com.release.easybasex.utils.ToastUtils;
import com.release.easybasex.widget.StateLayout;


/**
 * @author Mr.release
 * @create 2019/8/2
 * @Describe
 */
public abstract class QMUIBaseMvpActivity<V extends IView, P extends IPresenter<V>> extends QMUIBaseActivity implements IView {

    protected P mPresenter;
    protected StateLayout mStateLayout;

    protected abstract P createPresenter();

    @Override
    public void initView() {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateLayout = findViewById(R.id.state_layout);
    }

    @Override
    public void showLoading() {
        if (mStateLayout != null) {
            mStateLayout.setEmptyStatus(StateLayout.STATUS_LOADING);
            mStateLayout.show();
        }
    }

    @Override
    public void hide() {
        if (mStateLayout != null) {
            mStateLayout.hide();
        }
    }

    @Override
    public void showNoData() {
        if (mStateLayout != null) {
            mStateLayout.setEmptyStatus(StateLayout.STATUS_NO_DATA);
            mStateLayout.show();
        }
    }

    @Override
    public void showError() {
        if (mStateLayout != null) {
            mStateLayout.setEmptyStatus(StateLayout.STATUS_ERROR);
            mStateLayout.show();
            mStateLayout.setRetryListener(new StateLayout.OnRetryListener() {
                @Override
                public void onRetry() {
                    startNet();
                }
            });
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
