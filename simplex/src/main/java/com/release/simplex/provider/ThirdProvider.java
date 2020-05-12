package com.release.simplex.provider;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.release.easybasex.utils.DensityUtil;
import com.release.easybasex.utils.ToastUtils;
import com.release.simplex.R;
import com.release.simplex.mvp.model.ThirdNode;
import com.release.simplex.ui.act.ScreenshotAndWindowFloatActivity;
import com.release.simplex.ui.act.TimerAnimationActivity;
import com.release.simplex.ui.act.VLayoutActivity;
import com.release.simplex.ui.act.WebViewPlusActivity;

import org.jetbrains.annotations.NotNull;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class ThirdProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_layout;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        ThirdNode entity = (ThirdNode) data;
        helper.setText(R.id.tv_title, entity.getTitle());

        ImageView view = helper.getView(R.id.iv_arrow);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(DensityUtil.dpToPxInt(40), 0, 0, 0);
        view.setLayoutParams(params);
        helper.setVisible(R.id.iv_arrow, false);
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        ThirdNode entity = (ThirdNode) data;
        switch (entity.getTitle()) {
            case "送礼物倒计时按钮":
                context.startActivity(new Intent(context, TimerAnimationActivity.class));
                break;
        }
    }
}
