package com.znz.news.ui.mine;

import android.support.v7.widget.RecyclerView;

import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.MultiBean;
import com.znz.news.common.Constants;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class MineFavAct extends BaseAppListActivity<MultiBean> {

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Video));
        dataList.add(new MultiBean(Constants.MultiType.Picture));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Video));
        dataList.add(new MultiBean(Constants.MultiType.Picture));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Video));
        dataList.add(new MultiBean(Constants.MultiType.Picture));
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("我的收藏");
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
    protected void onRefreshSuccess(String response) {

    }

    @Override
    protected void onRefreshFail(String error) {

    }

}
