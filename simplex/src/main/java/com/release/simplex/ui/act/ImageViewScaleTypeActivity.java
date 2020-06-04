package com.release.simplex.ui.act;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.ui.adapter.ImageViewScaleTypeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        mTopBar.setTitle("ImageView的ScaleType");

        List typeList = new ArrayList(type.length);
        Collections.addAll(typeList, type);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ImageViewScaleTypeAdapter(R.layout.item_image_view_scale_type, typeList);
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mRvList.setAdapter(mAdapter);
    }
}
