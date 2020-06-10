package com.release.simplex;

import android.Manifest;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.release.cameralibrary.PermissionUtils;
import com.release.easybasex.base.BaseMvpActivity;
import com.release.simplex.mvp.contract.MainContract;
import com.release.simplex.mvp.model.FirstNode;
import com.release.simplex.mvp.model.MenuBean;
import com.release.simplex.mvp.model.SecondNode;
import com.release.simplex.mvp.model.ThirdNode;
import com.release.simplex.mvp.presenter.MainPersenter;
import com.release.simplex.ui.adapter.DataAdapter;
import com.release.simplex.utils.MenuUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.release.simplex.utils.Constants.PAGE;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class MainActivity extends BaseMvpActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private DataAdapter mAdapter;
    private boolean isRefresh = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPersenter();
    }

    @Override
    public void initView() {
        super.initView();
        mTopBar.setTitle("主页").setBackIconVisible(View.GONE);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DataAdapter();
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mRvList.setAdapter(mAdapter);

        PermissionUtils.checkAndReqkPermission(this, needPermissions);
    }

    public String[] needPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                mAdapter.setFooterWithEmptyEnable(false);
                mPresenter.requestData(isRefresh);
                mRefreshLayout.finishRefresh(1000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                int page = mAdapter.getData().size() / PAGE;
                mPresenter.requestData(isRefresh);
                mRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    @Override
    public void startNet() {
        mPresenter.requestData(isRefresh);
    }

    @Override
    public void loadData(Object data) {
        super.loadData(data);
        mAdapter.setList(getEntity());
    }

    private List<BaseNode> getEntity() {

        List<BaseNode> list = new ArrayList<>();
        MenuBean menuBean = JSON.parseObject(new MenuUtil().readAssets(this, "menu.json"), MenuBean.class);
        List<MenuBean.FirstBean> first = menuBean.getFirst();
        for (MenuBean.FirstBean firstBean : first) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            List<MenuBean.FirstBean.SecondBean> second = firstBean.getSecond();
            for (MenuBean.FirstBean.SecondBean secondBean : second) {
                List<BaseNode> thirdNodeList = new ArrayList<>();
                List<MenuBean.FirstBean.SecondBean.ThirdBean> third = secondBean.getThird();
                if (third != null) {
                    for (MenuBean.FirstBean.SecondBean.ThirdBean thirdBean : third) {
                        ThirdNode node = new ThirdNode(thirdBean.getTitle());
                        thirdNodeList.add(node);
                    }
                }
                SecondNode seNode = new SecondNode(thirdNodeList, secondBean.getTitle());
                secondNodeList.add(seNode);
            }
            FirstNode entity = new FirstNode(secondNodeList, firstBean.getTitle());
            list.add(entity);
        }
        return list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.exit(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
