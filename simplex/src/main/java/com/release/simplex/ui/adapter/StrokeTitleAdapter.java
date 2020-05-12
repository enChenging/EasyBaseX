package com.release.simplex.ui.adapter;

import com.alibaba.android.vlayout.LayoutHelper;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.release.simplex.R;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/5/11
 * @Describe
 */
public class StrokeTitleAdapter extends BaseVlayoutAdapter<String> {

    public StrokeTitleAdapter(LayoutHelper layoutHelper, List<String> data) {
        super(layoutHelper, R.layout.item_stroke_title, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item, int position, int offsetTotal) {
        helper.setText(R.id.tv_title, item);
    }
}
