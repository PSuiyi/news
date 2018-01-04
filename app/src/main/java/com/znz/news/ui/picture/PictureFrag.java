package com.znz.news.ui.picture;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.znz.news.R;
import com.znz.news.adapter.CoverFlowAdapter;
import com.znz.news.adapter.PictureAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.ImageBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.ui.common.SearchCommonActivity;
import com.znz.news.view.RecyclerCoverFlow;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class PictureFrag extends BaseAppListFragment<NewsBean> {

    private View header;
    private TextView tvTitle;
    private TextView tvContent;
    private RecyclerCoverFlow rvCoverFlow;
    private CoverFlowAdapter coverFlowAdapter;
    private List<String> topList = new ArrayList<>();
    private NewsBean topBean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 2};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜您想要的图片内容");
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
        adapter = new PictureAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_picture, null);
        adapter.addHeaderView(header);
        rvCoverFlow = bindViewById(header, R.id.rvCoverFlow);
        tvTitle = bindViewById(header, R.id.tvTitle);
        tvContent = bindViewById(header, R.id.tvContent);

        coverFlowAdapter = new CoverFlowAdapter(topList);
        rvCoverFlow.setOnItemSelectedListener(position -> {
            mDataManager.setValueToView(tvContent, topBean.getContentBanner().get(position).getDesc());
        });
        rvCoverFlow.setAdapter(coverFlowAdapter);
    }

    @Override
    protected void loadDataFromServer() {
//        Map<String, String> params = new HashMap<>();
//        params.put("page", "1");
//        params.put("pagesize", "20");
//        params.put("recRosition", "2");
//        mModel.requestNewsList(params, new ZnzHttpListener() {
//            @Override
//            public void onSuccess(JSONObject responseOriginal) {
//                super.onSuccess(responseOriginal);
//                topList.addAll(JSONArray.parseArray(responseObject.getString("list"), NewsBean.class));
//                coverFlowAdapter.notifyDataSetChanged();
//                if (topList.size() >= 2) {
//                    rvCoverFlow.scrollToPosition(1);
//                }
//            }
//
//            @Override
//            public void onFail(String error) {
//                super.onFail(error);
//            }
//        });
    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("contentType", "1");
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), NewsBean.class));
        if (currentAction == ACTION_PULL_TO_REFRESH) {
            if (!dataList.isEmpty()) {
                topBean = dataList.get(0);
                mDataManager.setValueToView(tvTitle, topBean.getContentTitle());

                if (!dataList.get(0).getContentBanner().isEmpty()) {
                    mDataManager.setValueToView(tvContent, topBean.getContentBanner().get(0).getDesc());
                    topList.clear();
                    for (ImageBean imageBean : dataList.get(0).getContentBanner()) {
                        topList.add(imageBean.getUrl());
                    }
                    coverFlowAdapter.setId(topBean.getContentId());
                    coverFlowAdapter.notifyDataSetChanged();
                    if (topList.size() >= 2) {
                        rvCoverFlow.scrollToPosition(1);
                    }
                }
                dataList.remove(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
