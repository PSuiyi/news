package com.znz.news.ui.home;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class ArticleDetailAct extends BaseAppListActivity implements View.OnLayoutChangeListener {

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
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.flComment)
    FrameLayout flComment;
    @Bind(R.id.ivFav)
    ImageView ivFav;
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
    private View header;
    private String id;
    private TextView tvTitle;
    private WebView wvHtml;
    private NewsBean bean;
    private WebSettings settings;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("文章详情");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new CommentAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_article, null);
        adapter.addHeaderView(header);
        tvTitle = bindViewById(header, R.id.tvTitle);
        wvHtml = bindViewById(header, R.id.wvHtml);

        llContainer.addOnLayoutChangeListener(this);

        settings = wvHtml.getSettings();
        settings.setSupportZoom(true);//是否支持缩放,默认为true
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //版本号控制，使图片能够适配
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 19) {
        } else {
            settings.setUseWideViewPort(true);
        }

        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        wvHtml.setWebViewClient(new WebViewClient() {
            // 点击网页里面的链接还是在当前的webView内部跳转，不跳转外部浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // 可以让webView处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
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
                if (bean.getIsCollected().equals("1")) {
                    ivFav.setImageResource(R.mipmap.yishoucang);
                } else {
                    ivFav.setImageResource(R.mipmap.shoucanghei);
                }

                if (!StringUtil.isBlank(bean.getContentBody())) {
                    wvHtml.loadDataWithBaseURL(null, bean.getContentBody(), "text/html", "utf-8", null);
                    if (Build.VERSION.SDK_INT > 19) {
                        settings.setTextZoom(400);
                    }
                }
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
