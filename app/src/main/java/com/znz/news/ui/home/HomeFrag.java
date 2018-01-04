package com.znz.news.ui.home;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.imageloder.GlideApp;
import com.znz.libvideo.videoplayer.GSYVideoManager;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.adapter.TypeHorizontalAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.BannerBean;
import com.znz.news.bean.CateBean;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;
import com.znz.news.ui.common.SearchCommonActivity;
import com.znz.news.ui.common.WebViewAct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFrag extends BaseAppListFragment<MultiBean> {

    private View header;
    private RecyclerView rvType;
    private LinearLayout llMore;
    private BGABanner mBanner;

    private List<CateBean> cateBeanList = new ArrayList<>();
    private TypeHorizontalAdapter typeAdapter;

    private List<NewsBean> newsBeanList = new ArrayList<>();
    private boolean mFull;
    private List<BannerBean> bannerBeanList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_home, 2};
    }

    @Override
    protected void initializeVariate() {
    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜索");
        znzToolBar.setEnableEdit(false);
        znzToolBar.setOnSearchClickListener(v -> {
            gotoActivity(SearchCommonActivity.class);
        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_home, null);
        adapter.addHeaderView(header);

        llMore = bindViewById(header, R.id.llMore);
        rvType = bindViewById(header, R.id.rvType);

        llMore.setOnClickListener(v -> {
            gotoActivity(TypeListAct.class);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(linearLayoutManager);

        typeAdapter = new TypeHorizontalAdapter(cateBeanList);
        rvType.setAdapter(typeAdapter);

        mBanner = bindViewById(header, R.id.mBanner);

        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideApp.with(activity)
                        .load(model)
                        .placeholder(R.mipmap.default_image_rect)
                        .error(R.mipmap.default_image_rect)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });


        mBanner.setDelegate((banner, itemView, model, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", bannerBeanList.get(position).getAdLink());
            bundle.putString("title", bannerBeanList.get(position).getAdName());
            gotoActivity(WebViewAct.class, bundle);
        });
    }

    @Override
    protected void loadDataFromServer() {

    }

    private void requestBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("pagesize", "10");
        mModel.requestBannerList(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bannerBeanList.clear();
                bannerBeanList.addAll(JSONArray.parseArray(responseObject.getString("list"), BannerBean.class));
                List<String> advUrls = new ArrayList<>();
                List<String> advTitles = new ArrayList<>();
                for (BannerBean bannerBean : bannerBeanList) {
                    advUrls.add(bannerBean.getAdBanner());
                    advTitles.add(bannerBean.getAdName());
                }
                mBanner.setData(advUrls, advTitles);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("page", "1");
        params2.put("pagesize", "10");
        mModel.requestCateOneList(params2, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                cateBeanList.clear();
                cateBeanList.addAll(JSONArray.parseArray(responseObject.getString("list"), CateBean.class));
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        newsBeanList.clear();
        newsBeanList.addAll(JSONArray.parseArray(responseJson.getString("list"), NewsBean.class));
        if (!newsBeanList.isEmpty()) {
            if (currentAction == ACTION_PULL_TO_REFRESH) {
                for (NewsBean newsBean : newsBeanList) {
                    if (newsBean.getIsTop().equals("1")) {
                        dataList.add(new MultiBean(Constants.MultiType.Top, newsBean));
                    }
                }
                requestBanner();
            }
            for (NewsBean newsBean : newsBeanList) {
                switch (newsBean.getContentType()) {
                    case "0":
                        dataList.add(new MultiBean(Constants.MultiType.Article, newsBean));
                        break;
                    case "1":
                        dataList.add(new MultiBean(Constants.MultiType.Picture, newsBean));
                        break;
                    case "2":
                        dataList.add(new MultiBean(Constants.MultiType.Video, newsBean));
                        break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }

    }

    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            GSYVideoPlayer.releaseAllVideos();
        }
    }
}
