package com.znz.news.ui.picture;

import android.support.v7.widget.RecyclerView;

import com.znz.news.R;
import com.znz.news.adapter.PictureAdapter;
import com.znz.news.base.BaseAppListFragment;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class PictureFrag extends BaseAppListFragment {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {

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
        adapter = new PictureAdapter(dataList);
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
