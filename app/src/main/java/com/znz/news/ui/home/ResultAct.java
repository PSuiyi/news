package com.znz.news.ui.home;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class ResultAct extends BaseAppListActivity<MultiBean> {
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private String cateId;
    private String cateName;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
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
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

}
