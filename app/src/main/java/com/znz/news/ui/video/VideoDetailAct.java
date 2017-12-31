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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
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
import com.znz.news.ui.picture.CommentListAct;

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

public class VideoDetailAct extends BaseAppListActivity<CommentBean> {

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
    private View header;

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
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("contentId", id);
        mModel.requestNewsDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), NewsBean.class);
                gsyVideoOption.setIsTouchWiget(true)
                        .setRotateViewAuto(false)
                        .setLockLand(false)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setSeekRatio(1)
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
                ivImage.setImageResource(R.mipmap.default_image_rect);
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

    @OnClick({R.id.flComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flComment:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(CommentListAct.class, bundle);
                break;
        }
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
}
