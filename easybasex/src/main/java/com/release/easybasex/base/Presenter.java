package com.release.easybasex.base;

/**
 * @author Mr.release
 * @create 2019/4/12
 * @Describe
 */
public interface Presenter {

    void loadData();

    void loadData(boolean isRefresh);

    void loadMoreData();
}
