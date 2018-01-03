package com.znz.news.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.bean.NewsBean;
import com.znz.news.ui.video.VideoDetailAct;

import java.util.List;

import butterknife.Bind;

public class VideoAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private final Activity activity;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.detailPlayer)
    StandardGSYVideoPlayer gsyVideoPlayer;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoAdapter(@Nullable List dataList, Activity activity) {
        super(R.layout.item_lv_video, dataList);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean bean) {
        setOnItemClickListener(this);

        mDataManager.setValueToView(tvTitle, bean.getContentTitle());
        HttpImageView ivImage = new HttpImageView(mContext);
        if (!bean.getContentBanner().isEmpty()) {
            ivImage.loadRectImage(bean.getContentBanner().get(0).getUrl());
        } else {
            ivImage.setImageResource(R.mipmap.default_image_rect);
        }
        ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(ivImage)
                .setUrl(bean.getVideoUrl())
                .setVideoTitle(bean.getContentTitle())
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setThumbPlay(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(helper.getAdapterPosition())
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                    }
                }).build(gsyVideoPlayer);

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsyVideoPlayer.startWindowFullscreen(activity, true, true);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getContentId());
        gotoActivity(VideoDetailAct.class, bundle);
    }
}
