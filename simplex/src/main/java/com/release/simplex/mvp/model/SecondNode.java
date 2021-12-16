package com.release.simplex.mvp.model;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;
    private int selectedItem;

    public SecondNode(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;

        setExpanded(false);
    }

    public String getTitle() {
        return title;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int isCheck){
        this.selectedItem = isCheck;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
