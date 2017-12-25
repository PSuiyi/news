package com.znz.news.ui.video;

import android.support.v7.widget.RecyclerView;

import com.znz.news.R;
import com.znz.news.adapter.VideoAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.ui.common.SearchCommonActivity;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class VideoFrag extends BaseAppListFragment {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 2};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜您想要的视频内容");
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
        adapter = new VideoAdapter(dataList);
        rvRefresh.setAdapter(adapter);
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
}
