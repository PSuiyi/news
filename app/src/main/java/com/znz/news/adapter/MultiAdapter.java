package com.znz.news.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.nine.NineGridView;
import com.znz.compass.znzlibray.views.recyclerview.BaseMultiItemQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.bean.MultiBean;
import com.znz.news.common.Constants;
import com.znz.news.ui.home.ArticleDetailAct;
import com.znz.news.ui.picture.PictureDetailAct;
import com.znz.news.ui.video.VideoDetailAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public MultiAdapter(List dataList) {
        super(dataList);
        addItemType(Constants.MultiType.Section, R.layout.item_lv_section);
        addItemType(Constants.MultiType.Top, R.layout.item_lv_top);
        addItemType(Constants.MultiType.Article, R.layout.item_lv_article);
        addItemType(Constants.MultiType.Video, R.layout.item_lv_video);
        addItemType(Constants.MultiType.Picture, R.layout.item_lv_picture);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBean bean) {
        setOnItemClickListener(this);
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                helper.setText(R.id.tvSection, bean.getSection());
                break;
            case Constants.MultiType.Top:
                break;
            case Constants.MultiType.Article:
                break;
            case Constants.MultiType.Video:
                StandardGSYVideoPlayer gsyVideoPlayer = helper.getView(R.id.detailPlayer);
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
                break;
            case Constants.MultiType.Picture:
                NineGridView nineGrid = helper.getView(R.id.nineGrid);
                List<String> urls = new ArrayList<>();
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                nineGrid.setList(urls);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                break;
            case Constants.MultiType.Top:
                gotoActivity(ArticleDetailAct.class);
                break;
            case Constants.MultiType.Article:
                gotoActivity(ArticleDetailAct.class);
                break;
            case Constants.MultiType.Video:
                gotoActivity(VideoDetailAct.class);
                break;
            case Constants.MultiType.Picture:
                gotoActivity(PictureDetailAct.class);
                break;

        }
    }

}
