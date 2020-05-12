package com.release.simplex.downLoad;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public interface DownloadListner {
    void onFinished();

    void onProgress(float progress);

    void onPause();

    void onCancel();
}
