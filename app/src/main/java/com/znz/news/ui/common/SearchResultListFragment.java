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
