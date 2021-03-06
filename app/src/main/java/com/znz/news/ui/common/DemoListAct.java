package com.znz.news.ui.common;

import android.support.v7.widget.RecyclerView;

import com.znz.news.R;
import com.znz.news.adapter.DemoAdapter;
import com.znz.news.base.BaseAppListActivity;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class DemoListAct extends BaseAppListActivity {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new DemoAdapter(dataList);
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
