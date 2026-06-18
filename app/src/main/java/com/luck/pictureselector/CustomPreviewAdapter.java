package com.luck.pictureselector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.adapter.PicturePreviewAdapter;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.OnViewTapListener;

/**
 * @author：luck
 * @date：2022/2/21 4:17 下午
 * @describe：CustomPreviewAdapter
 */
public class CustomPreviewAdapter extends PicturePreviewAdapter {

    @NonNull
    @Override
    public BasePreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BasePreviewHolder.ADAPTER_TYPE_IMAGE) {
            // 这里以重写自定义图片预览为例（使用 PhotoView 展示，兼容 16K 页面大小）
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ps_custom_preview_image, parent, false);
            return new CustomPreviewImageHolder(itemView);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    public static class CustomPreviewImageHolder extends BasePreviewHolder {

        public CustomPreviewImageHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void findViews(View itemView) {
            // coverImageView 已在基类中初始化
        }

        @Override
        protected void loadImage(LocalMedia media, int maxWidth, int maxHeight) {
            Glide.with(itemView.getContext())
                    .load(media.getAvailablePath())
                    .into(coverImageView);
        }

        @Override
        protected void onClickBackPressed() {
            coverImageView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (mPreviewEventListener != null) {
                        mPreviewEventListener.onBackPressed();
                    }
                }
            });
        }

        @Override
        protected void onLongPressDownload(LocalMedia media) {
            coverImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mPreviewEventListener != null) {
                        mPreviewEventListener.onLongPressDownload(media);
                    }
                    return false;
                }
            });
        }
    }
}
