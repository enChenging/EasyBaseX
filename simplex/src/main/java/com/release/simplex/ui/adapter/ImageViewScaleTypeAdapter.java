package com.release.simplex.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.release.easybasex.utils.DefIconFactory;
import com.release.easybasex.utils.ImageLoader;
import com.release.simplex.R;
import com.release.simplex.utils.Constants;

import java.util.List;


/**
 * @author Mr.release
 * @create 2020/6/3
 * @Describe
 */
public class ImageViewScaleTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ImageViewScaleTypeAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String name) {

        helper.setText(R.id.tv_type, name);
        ImageView imageView = helper.getView(R.id.iv_type);
        switch (name) {
            case "matrix":
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                break;
            case "fitXY":
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case "fitStart":
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case "fitCenter":
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case "fitEnd":
                imageView.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case "center":
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case "centerCrop":
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case "centerInside":
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
        }

        ImageLoader.load(getContext(), Constants.TEMP_IMAGE, imageView, DefIconFactory.provideIcon());
    }
}
