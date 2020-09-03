package com.release.easybasex.base;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
interface UiInterfaceAct {

    int getLayoutId();

    boolean isOriginalLayout();

    void initView();

    void initListener();

    void startNet();

}
