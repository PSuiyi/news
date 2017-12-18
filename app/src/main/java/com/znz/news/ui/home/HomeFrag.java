package com.znz.news.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.MultiBean;
import com.znz.news.common.Constants;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFrag extends BaseAppListFragment<MultiBean> {

    private View header;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_home};
    }

    @Override
    protected void initializeVariate() {
        dataList.add(new MultiBean(Constants.MultiType.Top));
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

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_home, null);
        adapter.addHeaderView(header);
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
