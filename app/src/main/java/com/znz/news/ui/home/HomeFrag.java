package com.znz.news.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
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

        mBanner.setAdapter((banner, itemView, model, position) -> {

        });


//        mBanner.setDelegate((banner, itemView, model, position) -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("url", advList.get(position).getUrl());
//            bundle.putString("title", advList.get(position).getName());
//            gotoActivity(WebViewAct.class, bundle);
//        });


    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("pagesize", "10");
        mModel.requestBannerList(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                List<BannerBean> bannerBeanList = JSONArray.parseArray(responseObject.getString("list"), BannerBean.class);
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
        params.put("contentType", "1");
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        newsBeanList.addAll(JSONArray.parseArray(responseJson.getString("list"), NewsBean.class));
        if (!newsBeanList.isEmpty()) {
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
    }

    @Override
    protected void onRefreshFail(String error) {

    }

}
