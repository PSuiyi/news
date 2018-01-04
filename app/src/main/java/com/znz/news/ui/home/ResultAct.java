package com.znz.news.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class ResultAct extends BaseAppListActivity<MultiBean> {
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
    @Bind(R.id.rvRecommend)
    RecyclerView rvRecommend;
    @Bind(R.id.llRecommend)
    LinearLayout llRecommend;
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private String cateId;
    private String cateName;

    private MultiAdapter recommendAdapter;
    private List<MultiBean> recommendList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_result, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("cateId")) {
            cateId = getIntent().getStringExtra("cateId");
        }

        if (getIntent().hasExtra("cateName")) {
            cateName = getIntent().getStringExtra("cateName");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName(cateName);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        recommendAdapter = new MultiAdapter(recommendList);
        rvRecommend.setLayoutManager(new LinearLayoutManager(activity));
        rvRecommend.setAdapter(recommendAdapter);
        recommendAdapter.addHeaderView(View.inflate(activity, R.layout.widget_pull_to_refresh_no_data, null));
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("cateId", cateId);
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        newsBeanList.clear();
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
            adapter.notifyDataSetChanged();
        } else {
            mDataManager.setViewVisibility(mSwipeRefreshLayout, false);
            mDataManager.setViewVisibility(llRecommend, true);

            //没有数据时取推荐
            Map<String, String> params = new HashMap<>();
            params.put("pagesize", "20");
            params.put("page", "1");
            mModel.requestNewsList(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    newsBeanList.clear();
                    newsBeanList.addAll(JSONArray.parseArray(responseObject.getString("list"), NewsBean.class));
                    for (NewsBean newsBean : newsBeanList) {
                        switch (newsBean.getContentType()) {
                            case "0":
                                recommendList.add(new MultiBean(Constants.MultiType.Article, newsBean));
                                dataList.add(new MultiBean(Constants.MultiType.Article, newsBean));
                                break;
                            case "1":
                                recommendList.add(new MultiBean(Constants.MultiType.Picture, newsBean));
                                break;
                            case "2":
                                recommendList.add(new MultiBean(Constants.MultiType.Video, newsBean));
                                break;
                        }
                    }
                    recommendAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        }
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
}
