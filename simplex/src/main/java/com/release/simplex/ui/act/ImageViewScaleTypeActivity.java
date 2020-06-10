package com.release.simplex.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.release.easybasex.base.BaseActivity;
import com.release.easybasex.utils.ShareUtil;
import com.release.simplex.R;
import com.release.simplex.ui.adapter.ImageViewScaleTypeAdapter;
import com.release.simplex.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author Mr.release
 * @create 2020/6/3
 * @Describe
 */

public class ImageViewScaleTypeActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private String[] type = {"matrix", "fitXY", "fitStart", "fitCenter", "fitEnd", "center", "centerCrop", "centerInside"};
    private ImageViewScaleTypeAdapter mAdapter;
    private ArrayList<Uri> uriList = new ArrayList();
    private Uri mUri;
    private File file = new File(Constants.FILE_PATH, "ScaleType.png");

    private String path = Environment.getExternalStorageDirectory() + File.separator + "com.ctrl.qizhi" + File.separator + "yunketangfile";
    private File testFile = new File(path, "12324.docx");

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {

        mTopBar.setTitle("ImageView的ScaleType");
        mTopBar.setRightText("分享").setRightTextColor(R.color.Orange).setOnRightClickListener(v -> {
//            ShareUtil.shareText(this,"我是分享内容");
            ShareUtil.shareImage(this, mUri);
//            ShareUtil.sendMoreImage(this, uriList);
//            ShareUtil.sendEmail(this, "2416677458@qq.com","release","HelloWorld");
        });

        List typeList = new ArrayList(type.length);
        Collections.addAll(typeList, type);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ImageViewScaleTypeAdapter(R.layout.item_image_view_scale_type, typeList);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mRvList.setAdapter(mAdapter);
        loadFile();
    }


    private void loadFile() {
        if (file.exists()) {
            mUri = Uri.fromFile(file);
            uriList.add(mUri);
            uriList.add(mUri);
            return;
        }
        OkHttpUtils
                .get()
                .url(Constants.TEMP_IMAGE)
                .build()
                .execute(new FileCallBack(Constants.FILE_PATH, "ScaleType.png") {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Logger.e("load--onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        String absolutePath = response.getAbsolutePath();
                        Logger.i("load---onResponse :" + absolutePath);
                        mUri = Uri.fromFile(file);
                        uriList.add(mUri);
                        uriList.add(mUri);
                    }

                });
    }


    /**
     * 打开文件
     */
    private void openFile() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(testFile);
        intent.setDataAndType(uri, "application/*");
//        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }


}
