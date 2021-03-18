package com.release.easybasex.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.release.easybasex.R;
import com.release.easybasex.utils.ToastUtils;
import com.release.easybasex.widget.StateLayout;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public abstract class BaseFragment extends Fragment implements UiInterfaceFrag {

    /**
     * 视图是否加载完毕
     */
    private boolean isViewPrepare;
    /**
     * 数据是否加载过了
     */
    private boolean hasLoadData;
    private Unbinder mUnbinder;
    protected Context mContext;
    protected LinearLayoutCompat mBaseViewFragment;
    protected StateLayout mStateLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View _view;
        if (isOriginalLayout()) {
            _view = LayoutInflater.from(mContext).inflate(R.layout.cyc_fragment_base, null);
            mBaseViewFragment = _view.findViewById(R.id.ll_fragment_base_view);
            LayoutInflater.from(mContext).inflate(getLayoutId(), mBaseViewFragment, true);
        } else {
            _view = inflater.inflate(getLayoutId(), container, false);
        }
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepare = true;
        mStateLayout = view.findViewById(R.id.state_layout);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        try {
            mUnbinder = ButterKnife.bind(this, view);
        } catch (Exception e) {

        }

        initView(view);
        initListener();
        useEventBus();
        lazyLoadDataIfPrepared();
    }

    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            lazyLoadDataIfPrepared();
    }

    private void lazyLoadDataIfPrepared() {
        if (getUserVisibleHint() && isViewPrepare && !hasLoadData) {
            startNet();
            hasLoadData = true;
        }
    }

    @Override
    public boolean isOriginalLayout() {
        return false;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void startNet() {

    }

    @Override
    public void onDestroyView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        try {
            if (mUnbinder != null)
                mUnbinder.unbind();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    protected void showLoading() {
        if (mStateLayout != null) {
            mStateLayout.setEmptyStatus(StateLayout.STATUS_LOADING);
            mStateLayout.show();
        }
    }

    protected void hide() {
        if (mStateLayout != null) {
            mStateLayout.hide();
        }
    }

    protected void showNoData() {
        if (mStateLayout != null) {
            mStateLayout.setEmptyStatus(StateLayout.STATUS_NO_DATA);
            mStateLayout.show();
        }
    }

    protected void showError() {
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

    protected void showError(String msg) {
        ToastUtils.show(msg);
    }

}
