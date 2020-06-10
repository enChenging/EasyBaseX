package com.release.simplex.utils;

import android.os.Environment;

import com.release.simplex.App;

import java.io.File;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public interface Constants {

    int PAGE = 20;
    int PAGE_TEN = 10;

    String TEMP_IMAGE = "https://cms-bucket.ws.126.net/2020/0604/2d48661bp00qbdjof0031c0009c005uc.png";
    String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + App.getInstance().getPackageName() + File.separator + "images";


}
