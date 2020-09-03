package com.release.easybasex.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.release.easybasex.R;
import com.release.easybasex.widget.EmptyLayout;

import org.greenrobot.eventbus.EventBus;

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
    protected EmptyLayout mEmptyLayoutFragment;

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
        if (isOriginalLayout()){
            _view =  LayoutInflater.from(mContext).inflate(R.layout.cyc_fragment_base, null);
            mBaseViewFragment = _view.findViewById(R.id.ll_fragment_base_view);
            LayoutInflater.from(mContext).inflate(getLayoutId(), mBaseViewFragment, true);
        }else{
            _view = inflater.inflate(getLayoutId(), container, false);
        }
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepare = true;
        mEmptyLayoutFragment = view.findViewById(R.id.empty_layout);
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

    public void showLoading() {
        if (mEmptyLayoutFragment != null) {
            mEmptyLayoutFragment.show();
            mEmptyLayoutFragment.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    public void hideLoading() {
        if (mEmptyLayoutFragment != null) {
            mEmptyLayoutFragment.hide();
        }
    }

    public void showError() {
        if (mEmptyLayoutFragment != null) {
            mEmptyLayoutFragment.show();
            mEmptyLayoutFragment.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
            mEmptyLayoutFragment.setRetryListener(new EmptyLayout.OnRetryListener() {
                @Override
                public void onRetry() {
                    startNet();
                }
            });
        }
    }
}
