package com.release.simplex.ui.act;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.utils.MyFirstContentProvider;

public class ContentProviderAcitivty extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_provider;
    }

    @Override
    public void initView() {
        super.initView();

        Uri boyUri = Uri.parse("content://"+ MyFirstContentProvider.AUTHORITY+"/boy");

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "张三");
        //getContentResolver().insert(boyUri, contentValues);
        //getContentResolver().delete(boyUri, )
        Cursor boyCursor = getContentResolver().query(boyUri, new String[]{"_id", "name"}, null, null, null);
        if (boyCursor != null) {
            while (boyCursor.moveToNext()) {
                Log.e("yunchong", "ID:" + boyCursor.getInt(boyCursor.getColumnIndex("_id")) + "  name:" + boyCursor.getString(boyCursor.getColumnIndex("name")));
            }
            boyCursor.close();
        }

        Uri girlUri = Uri.parse("content://"+ MyFirstContentProvider.AUTHORITY+"/girl");
        contentValues.clear();
        //contentValues.put("name", "李四");
        //getContentResolver().insert(girlUri, contentValues);
        Cursor girlCursor = getContentResolver().query(girlUri, new String[]{"_id", "name"}, null, null, null);
        if (girlCursor != null) {
            while (girlCursor.moveToNext()) {
                Log.e("yunchong", "ID:" + girlCursor.getInt(girlCursor.getColumnIndex("_id"))
                        + "  name:" + girlCursor.getString(girlCursor.getColumnIndex("name")));
            }
            girlCursor.close();
        }
    }
}
