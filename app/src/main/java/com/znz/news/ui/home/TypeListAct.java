package com.znz.news.ui.home;

import android.support.v7.widget.RecyclerView;

import com.znz.news.R;
import com.znz.news.adapter.TypeAdapter;
import com.znz.news.base.BaseAppListActivity;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class TypeListAct extends BaseAppListActivity {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("内容分类");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new TypeAdapter(dataList);
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
