package com.znz.news.ui.picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.ViewPageAdapter;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.bean.ImageBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.event.EventList;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/12/18 2017
 * User： PSuiyi
 * Description：
 */

public class PictureDetailAct extends BaseAppActivity implements View.OnLayoutChangeListener {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.flComment)
    FrameLayout flComment;
    @Bind(R.id.ivFav)
    ImageView ivFav;
    @Bind(R.id.commonViewPager)
    ViewPager commonViewPager;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.tvCountComment)
    TextView tvCountComment;
    @Bind(R.id.tvCountView)
    TextView tvCountView;
    @Bind(R.id.flView)
    FrameLayout flView;
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

    private List<Fragment> fragmentList = new ArrayList<>();
    private String id;
    private NewsBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_picture_detail};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {
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
                mDataManager.setValueToView(tvCountView, bean.getClickNum(), "0");
                mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum(), "0");

                if (bean.getIsCollected().equals("1")) {
                    ivFav.setImageResource(R.mipmap.yishoucang);
                } else {
                    ivFav.setImageResource(R.mipmap.shoucangbai);
                }

                if (!bean.getContentBanner().isEmpty()) {
                    int total = bean.getContentBanner().size();
                    if (!StringUtil.isBlank(bean.getContentBanner().get(0).getDesc())) {
                        tvContent.setText(1 + "/" + total + " " + bean.getContentBanner().get(0).getDesc());
                    } else {
                        tvContent.setText(1 + "/" + total);
                    }

                    for (ImageBean imageBean : bean.getContentBanner()) {
                        fragmentList.add(PictureDetailFrag.newInstance(imageBean));
                    }
                    commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), fragmentList));
                    commonViewPager.setOffscreenPageLimit(fragmentList.size());
                    commonViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (!StringUtil.isBlank(bean.getContentBanner().get(position).getDesc())) {
                                tvContent.setText(position + 1 + "/" + total + " " + bean.getContentBanner().get(position).getDesc());
                            } else {
                                tvContent.setText(position + 1 + "/" + total);
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.flComment, R.id.ivFav, R.id.tvSend, R.id.tvComment})
    public void onViewClicked(View view) {
        Map<String, String> params = new HashMap<>();
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.flComment:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(CommentListAct.class, bundle);
                break;
            case R.id.ivFav:
                if (bean.getIsCollected().equals("1")) {
                    params.put("contentId", id);
                    mModel.requestFavCancel(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            mDataManager.showToast("取消收藏成功");
                            ivFav.setImageResource(R.mipmap.shoucangbai);
                            bean.setIsCollected("0");
                            EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_FAV));
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                } else {
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

                        bean.setEvaluateNum(StringUtil.getNumUP(bean.getEvaluateNum()));
                        mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum(), "0");

                        EventBus.getDefault().post(new EventList(EventTags.LIST_COMMENT, bean.getContentId()));
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventList event) {
        if (event.getFlag() == EventTags.LIST_COMMENT_DETAIL) {
            if (event.getValue().equals(id)) {
                bean.setEvaluateNum(StringUtil.getNumUP(bean.getEvaluateNum()));
                mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum(), "0");
            }
        }
    }
}
