package com.znz.news.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.znz.news.R;
import com.znz.news.adapter.CommentAdapter;
import com.znz.news.base.BaseAppListActivity;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailAct extends BaseAppListActivity {

    private View header;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("文章详情");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new CommentAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_article, null);
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
