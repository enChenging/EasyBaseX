package com.release.easybasex.base;

/**
 * @author Mr.release
 * @create 2019/4/1
 * @Describe
 */
public interface IView<T> {

    /**
     * 加载数据
     */
    void loadData(T data);
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络异常，点击重试
     */
    void showError();

    /**
     * 显示错误信息
     */
    void showError(String msg);
}
