package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.ui.video.VideoDetailAct;

import java.util.List;

import butterknife.Bind;

public class VideoAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.detailPlayer)
    StandardGSYVideoPlayer gsyVideoPlayer;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_video, dataList);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);

        HttpImageView ivImage = new HttpImageView(mContext);
        ivImage.setImageResource(R.mipmap.default_image_rect);
        ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(ivImage)
                .setUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4")
                .setVideoTitle("测试视频")
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
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
        gsyVideoPlayer.getFullscreenButton().setVisibility(View.GONE);

//        //设置全屏按键功能
//        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resolveFullBtn(gsyVideoPlayer);
//            }
//        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(VideoDetailAct.class);
    }
}
