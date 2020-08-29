package com.release.easybasex.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * @author Mr.release
 * @create 2019/4/19
 * @Describe
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V>, LifecycleObserver {

    protected V mView;
    protected AndroidLifecycleScopeProvider scopeProvider;

    @Override
    public void attachView(V view) {
        this.mView = view;
        if (mView instanceof LifecycleOwner) {
            scopeProvider = AndroidLifecycleScopeProvider.from((LifecycleOwner) mView);
        }
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void checkViewAttached(){
        if (mView == null) throw  new RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner){
        owner.getLifecycle().removeObserver(this);
    }
}
