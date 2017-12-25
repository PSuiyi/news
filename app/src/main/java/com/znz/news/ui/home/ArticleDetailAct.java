package com.znz.news.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.CommentAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.ui.picture.CommentListAct;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailAct extends BaseAppListActivity {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.rvCommonRefresh)
    RecyclerView rvCommonRefresh;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.flComment)
    FrameLayout flComment;
    @Bind(R.id.ivFav)
    ImageView ivFav;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.flComment)
    public void onViewClicked() {
        gotoActivity(CommentListAct.class);
    }
}
