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
import com.release.simplex.R;
import com.release.simplex.ui.act.DownLoadActivity;
import com.release.simplex.mvp.model.SecondNode;
import com.release.simplex.ui.act.IjkPlayerActivity;
import com.release.simplex.ui.act.ImageViewScaleTypeActivity;
import com.release.simplex.ui.act.ScreenshotAndWindowFloatActivity;
import com.release.simplex.ui.act.ThreadActivity;
import com.release.simplex.ui.act.VLayoutActivity;
import com.release.simplex.ui.act.WebViewPlusActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class SecondProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_layout;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        SecondNode entity = (SecondNode) data;
        helper.setText(R.id.tv_title, entity.getTitle());
        ImageView view = helper.getView(R.id.iv_arrow);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        List<BaseNode> childNode = entity.getChildNode();
        if (childNode.size() == 0) {
            helper.setVisible(R.id.iv_arrow, false);
            params.setMargins(DensityUtil.dpToPxInt(10), 0, 0, 0);
        } else {
            helper.setVisible(R.id.iv_arrow, true);
            params.setMargins(DensityUtil.dpToPxInt(40), 0, 0, 0);
        }
        view.setLayoutParams(params);
        if (entity.isExpanded()) {
            helper.setImageResource(R.id.iv_arrow, R.drawable.ic_keyboard_arrow_down_blue_24dp);
        } else {
            helper.setImageResource(R.id.iv_arrow, R.drawable.ic_keyboard_arrow_right_blue_24dp);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;
        if (entity.isExpanded()) {//收起
            getAdapter().collapse(position);
        } else {//打开
            getAdapter().expandAndCollapseOther(position);
        }

        if (helper.getView(R.id.iv_arrow).getVisibility() == View.INVISIBLE) {
            switch (entity.getTitle()) {
                case "截图与悬浮窗":
                    context.startActivity(new Intent(context, ScreenshotAndWindowFloatActivity.class));
                    break;
                case "WebViewPlus":
                    context.startActivity(new Intent(context, WebViewPlusActivity.class));
                    break;
                case "VLayout使用":
                    context.startActivity(new Intent(context, VLayoutActivity.class));
                    break;
                case "下载":
                    context.startActivity(new Intent(context, DownLoadActivity.class));
                    break;
                case "单线程复用":
                    context.startActivity(new Intent(context, ThreadActivity.class));
                    break;
                case "ijk内核播放器":
                    context.startActivity(new Intent(context, IjkPlayerActivity.class));
                    break;
                case "ImageView的ScaleType":
                    context.startActivity(new Intent(context, ImageViewScaleTypeActivity.class));
                    break;
            }
        }
    }
}
