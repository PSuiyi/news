package com.znz.news.ui.common;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.news.R;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Date： 2017/5/23 2017
 * User： PSuiyi
 * Description：
 */

public class SearchResultListFragment extends BaseAppListFragment {
    @Bind(R.id.rvCommonRefresh)
    RecyclerView rvCommonRefresh;
    private String searchContent;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout, 1};
    }

    public static SearchResultListFragment newinstance(String searchContent) {
        Bundle args = new Bundle();
        args.putString("searchContent", searchContent);
        SearchResultListFragment listFragment = new SearchResultListFragment();
        listFragment.setArguments(args);
        return listFragment;
    }

    @Override
    protected void initializeVariate() {
        if (getArguments() != null) {
            searchContent = getArguments().getString("searchContent");
        }
    }

    @Override
    protected void initializeNavigation() {
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
//        adapter = new MultiAdapter(dataList);
//        rvCommonRefresh.setLayoutManager(new LinearLayoutManager(activity));
//        rvCommonRefresh.setAdapter(adapter);
//
//        switch (mDataManager.readTempData(Constants.SearchType.SEARCHTYPE)) {
//            case "首页":
//                llResult.setVisibility(View.GONE);
//                break;
//            case "电话会议":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("电话会议搜索结果");
//                break;
//            case "用户列表":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("用户搜索结果");
//                break;
//            case "专题研究":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("专题研究搜索结果");
//                break;
//            case "圈子":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("相关圈子");
//                break;
//            case "动态":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("相关动态");
//                break;
//            case "路演":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("相关路演");
//                break;
//            case "分析师":
//                llResult.setVisibility(View.VISIBLE);
//                tvResult.setText("相关分析师");
//                break;
//        }
    }

    @Override
    protected void loadDataFromServer() {
    }


    @Override
    protected void onRefreshSuccess(String response) {
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        EventManager.register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        switch (event.getFlag()) {
            case EventTags.REFRESH_SEARCH_VALUE:
                searchContent = event.getValue();
                hideNoData();
                resetRefresh();
                break;
        }
    }
}
