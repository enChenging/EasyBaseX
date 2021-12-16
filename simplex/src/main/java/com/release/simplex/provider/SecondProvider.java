package com.release.simplex.provider;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.release.easybasex.utils.DensityUtil;
import com.release.simplex.R;
import com.release.simplex.mvp.model.SecondNode;

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
        Log.i("cyc", "getSelectedItem: " + entity.getSelectedItem());
        if (entity.getSelectedItem() == helper.getLayoutPosition()) {
            helper.setBackgroundColor(R.id.vLayout, Color.RED);
            entity.setSelectedItem(-1);
        } else {
            helper.setBackgroundColor(R.id.vLayout, Color.WHITE);
        }
    }
}
