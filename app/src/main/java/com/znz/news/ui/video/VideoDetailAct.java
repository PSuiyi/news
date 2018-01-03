package com.znz.news.ui.video;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.listener.LockClickListener;
import com.znz.libvideo.videoplayer.utils.OrientationUtils;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.CommentAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.CommentBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;
import com.znz.news.ui.picture.CommentListAct;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class VideoDetailAct extends BaseAppListActivity<CommentBean> implements View.OnLayoutChangeListener {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.rvCommonRefresh)
    RecyclerView rvCommonRefresh;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.flComment)
    FrameLayout flComment;
    @Bind(R.id.ivFav)
    ImageView ivFav;
    @Bind(R.id.detailPlayer)
    StandardGSYVideoPlayer detailPlayer;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.tvCountComment)
    TextView tvCountComment;
    @Bind(R.id.tvCountView)
    TextView tvCountView;
    @Bind(R.id.flView)
    FrameLayout flView;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvComment)
    TextView tvComment;
    @Bind(R.id.llComment1)
    LinearLayout llComment1;
    @Bind(R.id.tvSend)
    TextView tvSend;
    @Bind(R.id.llComment2)
    LinearLayout llComment2;
    @Bind(R.id.llContainer)
    LinearLayout llContainer;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    protected GSYVideoOptionBuilder gsyVideoOption;
    private String id;
    private NewsBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_video_detail, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("视频详情");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new CommentAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

//        resolveNormalVideoUI();

        detailPlayer.getFullscreenButton().setOnClickListener(v -> {
            //直接横屏
            orientationUtils.resolveByClick();

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer.startWindowFullscreen(activity, true, true);
        });

        gsyVideoOption = new GSYVideoOptionBuilder();

        llContainer.addOnLayoutChangeListener(this);
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("contentId", id);
        mModel.requestNewsDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSON.parseObject(responseOriginal.getString("data"), NewsBean.class);

                mDataManager.setValueToView(tvTitle, bean.getContentTitle());
                mDataManager.setValueToView(tvCountView, bean.getClickNum());
                mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum());

                if (bean.getIsCollected().equals("1")) {
                    ivFav.setImageResource(R.mipmap.yishoucang);
                } else {
                    ivFav.setImageResource(R.mipmap.shoucanghei);
                }

                gsyVideoOption.setIsTouchWiget(true)
                        .setRotateViewAuto(false)
                        .setLockLand(false)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setSeekRatio(1)
                        .setThumbPlay(true)
                        .setCacheWithPlay(false)
                        .setStandardVideoAllCallBack(new SampleListener() {
                            @Override
                            public void onPrepared(String url, Object... objects) {
                                super.onPrepared(url, objects);
                                //开始播放了才能旋转和全屏
                                orientationUtils.setEnable(true);
                                isPlay = true;
                            }

                            @Override
                            public void onEnterFullscreen(String url, Object... objects) {
                                super.onEnterFullscreen(url, objects);
                            }

                            @Override
                            public void onAutoComplete(String url, Object... objects) {
                                super.onAutoComplete(url, objects);
                            }

                            @Override
                            public void onClickStartError(String url, Object... objects) {
                                super.onClickStartError(url, objects);
                            }

                            @Override
                            public void onQuitFullscreen(String url, Object... objects) {
                                super.onQuitFullscreen(url, objects);
                                if (orientationUtils != null) {
                                    orientationUtils.backToProtVideo();
                                }
                            }
                        })
                        .setLockClickListener(new LockClickListener() {
                            @Override
                            public void onClick(View view, boolean lock) {
                                if (orientationUtils != null) {
                                    //配合下方的onConfigurationChanged
                                    orientationUtils.setEnable(!lock);
                                }
                            }
                        });


                HttpImageView ivImage = new HttpImageView(activity);
                if (!bean.getContentBanner().isEmpty()) {
                    ivImage.loadRectImage(bean.getContentBanner().get(0).getUrl());
                } else {
                    ivImage.setImageResource(R.mipmap.default_image_rect);
                }
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                gsyVideoOption.setThumbImageView(ivImage)
                        .setUrl(bean.getVideoUrl())
                        .build(detailPlayer);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("contentId", id);
        return mModel.requestCommentList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), CommentBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            GSYVideoPlayer.releaseAllVideos();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

    @OnClick({R.id.flComment, R.id.ivFav, R.id.tvSend, R.id.tvComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flComment:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(CommentListAct.class, bundle);
                break;
            case R.id.ivFav:
                if (bean.getIsCollected().equals("1")) {
                    Map<String, String> params = new HashMap<>();
                    params.put("contentId", id);
                    mModel.requestFavCancel(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            mDataManager.showToast("取消收藏成功");
                            ivFav.setImageResource(R.mipmap.shoucanghei);
                            bean.setIsCollected("0");
                            EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_FAV));
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("contentId", id);
                    mModel.requestFavAdd(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            mDataManager.showToast("收藏成功");
                            ivFav.setImageResource(R.mipmap.yishoucang);
                            bean.setIsCollected("1");
                            EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_FAV));
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                }
                break;
            case R.id.tvComment:
                mDataManager.setViewVisibility(llComment1, false);
                mDataManager.setViewVisibility(llComment2, true);
                mDataManager.toggleEditTextFocus(etComment, true);
                break;
            case R.id.tvSend:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etComment))) {
                    mDataManager.showToast("请输入评论内容");
                    return;
                }
                params.put("contentId", id);
                params.put("EContent", mDataManager.getValueFromView(etComment));
                mModel.requestCommentAdd(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("评论成功");
                        etComment.setText("");
                        mDataManager.setViewVisibility(llComment1, true);
                        mDataManager.setViewVisibility(llComment2, false);

                        resetRefresh();

                        bean.setEvaluateNum(StringUtil.getNumUP(bean.getEvaluateNum()));
                        mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum(), "0");
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mDataManager.getDeviceHeight(activity) / 4)) {
            ZnzLog.e("监听到软键盘---->" + "弹起....");
            runOnUiThread(() -> {
                mDataManager.setViewVisibility(llComment1, false);
                mDataManager.setViewVisibility(llComment2, true);
            });
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mDataManager.getDeviceHeight(activity) / 4)) {
            ZnzLog.e("监听到软键盘---->" + "关闭....");
            runOnUiThread(() -> {
                mDataManager.setViewVisibility(llComment1, true);
                mDataManager.setViewVisibility(llComment2, false);
            });
        }
    }
}
