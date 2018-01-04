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
import com.znz.news.bean.ImageBean;
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
        HttpImageView ivImage;
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                helper.setText(R.id.tvSection, bean.getSection());
                break;
            case Constants.MultiType.Top:
                helper.setText(R.id.tvTitle, bean.getNewsBean().getContentTitle());
                helper.setText(R.id.tvCountComment, bean.getNewsBean().getEvaluateNum());
                break;
            case Constants.MultiType.Article:
                helper.setText(R.id.tvTitle, bean.getNewsBean().getContentTitle());
                ivImage = helper.getView(R.id.ivImage);
                if (!bean.getNewsBean().getContentBanner().isEmpty()) {
                    ivImage.loadRectImage(bean.getNewsBean().getContentBanner().get(0).getUrl());
                } else {
                    ivImage.setImageResource(R.mipmap.default_image_rect);
                }
                helper.setText(R.id.tvCountComment, bean.getNewsBean().getEvaluateNum());
                break;
            case Constants.MultiType.Video:
                helper.setText(R.id.tvTitle, bean.getNewsBean().getContentTitle());
                StandardGSYVideoPlayer gsyVideoPlayer = helper.getView(R.id.detailPlayer);
                ivImage = new HttpImageView(mContext);
                if (!bean.getNewsBean().getContentBanner().isEmpty()) {
                    ivImage.loadRectImage(bean.getNewsBean().getContentBanner().get(0).getUrl());
                } else {
                    ivImage.setImageResource(R.mipmap.default_image_rect);
                }
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                gsyVideoOptionBuilder
                        .setIsTouchWiget(false)
                        .setThumbImageView(ivImage)
                        .setUrl(bean.getNewsBean().getVideoUrl())
                        .setVideoTitle(bean.getNewsBean().getContentTitle())
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
                //设置全屏按键功能
                gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gsyVideoPlayer.startWindowFullscreen(mContext, true, true);
                    }
                });
                break;
            case Constants.MultiType.Picture:
                helper.setText(R.id.tvTitle, bean.getNewsBean().getContentTitle());
                NineGridView nineGrid = helper.getView(R.id.nineGrid);
                if (!bean.getNewsBean().getContentBanner().isEmpty()) {
                    List<String> urls = new ArrayList<>();
                    if (bean.getNewsBean().getContentBanner().size() >= 3) {
                        for (int i = 0; i < 3; i++) {
                            urls.add(bean.getNewsBean().getContentBanner().get(i).getUrl());
                        }
                    } else {
                        for (ImageBean imageBean : bean.getNewsBean().getContentBanner()) {
                            urls.add(imageBean.getUrl());
                        }
                    }
                    nineGrid.setList(urls);
                    mDataManager.setViewVisibility(nineGrid, true);
                } else {
                    mDataManager.setViewVisibility(nineGrid, false);
                }
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
                bundle.putString("id", bean.getNewsBean().getContentId());
                gotoActivity(ArticleDetailAct.class, bundle);
                break;
            case Constants.MultiType.Article:
                bundle.putString("id", bean.getNewsBean().getContentId());
                gotoActivity(ArticleDetailAct.class, bundle);
                break;
            case Constants.MultiType.Video:
                bundle.putString("id", bean.getNewsBean().getContentId());
                gotoActivity(VideoDetailAct.class, bundle);
                break;
            case Constants.MultiType.Picture:
                bundle.putString("id", bean.getNewsBean().getContentId());
                gotoActivity(PictureDetailAct.class, bundle);
                break;

        }
    }

}
