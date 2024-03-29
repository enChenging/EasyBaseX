package com.release.easybasex.base;

/**
 * @author Mr.release
 * @create 2019/4/1
 * @Describe
 */
public interface IView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏
     */
    void hide();

    /**
     * 显示提示信息
     */
    void showMsg(String msg);

    /**
     * 显示网络异常，点击重试
     */
    void showError();

    /**
     * 显示错误信息
     */
    void showError(String msg);

    /**
     *  显示没有数据
     */
    void showNoData();
}
