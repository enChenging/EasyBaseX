package com.release.simplex.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mr.release
 * @create 2020/5/6
 * @Describe
 */
public class MenuUtil {

    public String readAssets(Context context, String fName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fName);
            byte[] bytes = new byte[1024];
            int length;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((length = is.read(bytes)) != -1) {
                baos.write(bytes, 0, length);
            }
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
