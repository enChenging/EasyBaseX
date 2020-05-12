package com.release.simplex.ui.act;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.BaseLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.blankj.utilcode.util.ConvertUtils;
import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.ui.adapter.StrokeCardAdapter;
import com.release.simplex.ui.adapter.StrokeHistoryAdapter;
import com.release.simplex.ui.adapter.StrokeOrderAdapter;
import com.release.simplex.ui.adapter.StrokeTitleAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Mr.release
 * @create 2020/5/11
 * @Describe
 */

public class VLayoutActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private VirtualLayoutManager mVirtualLayoutManager;
    private DelegateAdapter mDelegateAdapter;
    private LinkedList<DelegateAdapter.Adapter> mAdapters;
    private StrokeCardAdapter mStrokeCardAdapter;
    private StrokeHistoryAdapter mStrokeHistoryAdapter;
    private StrokeOrderAdapter mStrokeOrderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vlayout;
    }

    @Override
    public void initView() {
        mTopBar.setTitle("VLayoutSample");

        mVirtualLayoutManager = new VirtualLayoutManager(this);
        mDelegateAdapter = new DelegateAdapter(mVirtualLayoutManager, false);

        mRvList.setLayoutManager(mVirtualLayoutManager);
        mRvList.setAdapter(mDelegateAdapter);

        mAdapters = new LinkedList<>();

        initHeader();
        initHistoryTitle();
        initHistoryList();
        initOrderTitle();
        initOrderList();

        mDelegateAdapter.setAdapters(mAdapters);
        initData();
    }

    private void initHeader() {

        mVirtualLayoutManager.setLayoutViewFactory(ImageView::new);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int dp15 = ConvertUtils.dp2px(15);
        linearLayoutHelper.setPadding(dp15, dp15, dp15, dp15);
        linearLayoutHelper.setDividerHeight(dp15);
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.frame_gray_background_color));

        mStrokeCardAdapter = new StrokeCardAdapter(linearLayoutHelper, null);
        mStrokeCardAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        mAdapters.add(mStrokeCardAdapter);
    }

    private void initHistoryTitle() {

        List<String> title = new ArrayList<>();
        title.add("历史行程");
        StrokeTitleAdapter strokeTitleAdapter = new StrokeTitleAdapter(new LinearLayoutHelper(), title);
        mAdapters.add(strokeTitleAdapter);

    }

    private void initHistoryList() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int dp15 = ConvertUtils.dp2px(15);
        linearLayoutHelper.setPadding(dp15, dp15, dp15, dp15);
        linearLayoutHelper.setDividerHeight(dp15);
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.frame_gray_background_color));
        mStrokeHistoryAdapter = new StrokeHistoryAdapter(linearLayoutHelper, null);

        mAdapters.add(mStrokeHistoryAdapter);
    }

    private void initOrderTitle() {
        List<String> title = new ArrayList<>();
        title.add("推荐订单");

        StrokeTitleAdapter strokeTitleAdapter = new StrokeTitleAdapter(new LinearLayoutHelper(), title);
        mAdapters.add(strokeTitleAdapter);
    }

    private void initOrderList() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int dp15 = ConvertUtils.dp2px(15);
        linearLayoutHelper.setPadding(dp15, dp15, dp15, dp15);
        linearLayoutHelper.setDividerHeight(dp15);
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.frame_gray_background_color));
        mStrokeOrderAdapter = new StrokeOrderAdapter(linearLayoutHelper, null);

        mAdapters.add(mStrokeOrderAdapter);
    }

    private void initData() {

        List<String> todoTestBeans = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            todoTestBeans.add("" + i);
        }

        mStrokeCardAdapter.setNewData(todoTestBeans);
        mStrokeHistoryAdapter.setNewData(todoTestBeans);
        mStrokeOrderAdapter.setNewData(todoTestBeans);
    }
}
