package com.release.simplex;

import android.Manifest;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.release.cameralibrary.PermissionUtils;
import com.release.easybasex.base.BaseMvpActivity;
import com.release.simplex.mvp.contract.MainContract;
import com.release.simplex.mvp.model.FirstNode;
import com.release.simplex.mvp.model.MenuBean;
import com.release.simplex.mvp.model.SecondNode;
import com.release.simplex.mvp.model.ThirdNode;
import com.release.simplex.mvp.presenter.MainPersenter;
import com.release.simplex.ui.act.AutoCompleteTextViewActivity;
import com.release.simplex.ui.act.ContentProviderAcitivty;
import com.release.simplex.ui.act.DownLoadActivity;
import com.release.simplex.ui.act.IjkPlayerActivity;
import com.release.simplex.ui.act.ImageViewScaleTypeActivity;
import com.release.simplex.ui.act.ScreenshotAndWindowFloatActivity;
import com.release.simplex.ui.act.ThreadActivity;
import com.release.simplex.ui.act.TimerAnimationActivity;
import com.release.simplex.ui.act.VLayoutActivity;
import com.release.simplex.ui.act.WebViewPlusActivity;
import com.release.simplex.ui.adapter.DataAdapter;
import com.release.simplex.utils.MenuUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPersenter();
    }

//    @Override
//    public boolean isOriginalLayout() {
//        return true;
//    }

//    @Override
//    protected void initThemeColor() {
//        StatusBarUtil.setTransparent(this);
//    }

    @Override
    public void initView() {
        mTopBar.setTitle("主页").setBackIconVisible(View.GONE);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DataAdapter();
        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mRvList.setAdapter(mAdapter);

        PermissionUtils.checkAndReqkPermission(this, needPermissions);

//        showLoading();
//        showError();
//        showNoData();
//        showTip(TipLoadDialog.ICON_TYPE_LOADING2,"正在加载...");
    }

    public String[] needPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
                int page = mAdapter.getData().size() / PAGE;
                mPresenter.requestData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                mPresenter.requestData(true);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                int itemViewType = adapter.getItemViewType(position);
                switch (itemViewType) {
                    case 1:
                        mAdapter.expandOrCollapse(position, true, true, 110);
                        break;
                    case 2:
                        List<SecondNode> data2 = (List<SecondNode>) adapter.getData();
                        SecondNode secondNode = data2.get(position);
                        if (secondNode.isExpanded()) {
                            //收起
                            mAdapter.collapse(position);
                        } else {
                            //打开
                            mAdapter.expandAndCollapseOther(position);
                        }
                        switch (secondNode.getTitle()) {
                            case "截图与悬浮窗":
                                startAct(ScreenshotAndWindowFloatActivity.class);
                                break;
                            case "WebViewPlus":
                                startAct(WebViewPlusActivity.class);
                                break;
                            case "VLayout使用":
                                startAct(VLayoutActivity.class);
                                break;
                            case "下载":
                                startAct(DownLoadActivity.class);
                                break;
                            case "单线程复用":
                                startAct(ThreadActivity.class);
                                break;
                            case "ijk内核播放器":
                                startAct(IjkPlayerActivity.class);
                                break;
                            case "ImageView的ScaleType":
                                startAct(ImageViewScaleTypeActivity.class);
                                break;
                            case "AutoCompleteTextView":
                                startAct(AutoCompleteTextViewActivity.class);
                                break;
                            case "ContentProvider":
                                startAct(ContentProviderAcitivty.class);
                                break;
                        }
                        break;
                    case 3:
                        List<ThirdNode> data3 = (List<ThirdNode>) adapter.getData();
                        ThirdNode thirdNode = data3.get(position);
                        switch (thirdNode.getTitle()) {
                            case "送礼物倒计时按钮":
                                startAct(TimerAnimationActivity.class);
                                break;
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void startNet() {
        mPresenter.requestData(false);
    }

    @Override
    public void loadData(Object data, boolean isRefresh) {
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
