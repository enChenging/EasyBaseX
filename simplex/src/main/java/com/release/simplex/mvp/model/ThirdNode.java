package com.release.simplex.mvp.model;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class ThirdNode extends BaseNode {
    private String title;

    public ThirdNode(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
