package com.release.simplex.ui.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.release.simplex.mvp.model.FirstNode;
import com.release.simplex.mvp.model.SecondNode;
import com.release.simplex.mvp.model.ThirdNode;
import com.release.simplex.provider.FirstProvider;
import com.release.simplex.provider.SecondProvider;
import com.release.simplex.provider.ThirdProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class DataAdapter extends BaseNodeAdapter {

    public DataAdapter() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
        addNodeProvider(new ThirdProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        } else if (node instanceof ThirdNode) {
            return 3;
        }
        return -1;
    }
}
