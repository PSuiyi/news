package com.znz.news.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.adapter.TypeHorizontalAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.MultiBean;
import com.znz.news.common.Constants;
import com.znz.news.ui.common.SearchCommonActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFrag extends BaseAppListFragment<MultiBean> {

    private View header;
    private RecyclerView rvType;
    private LinearLayout llMore;
    private BGABanner mBanner;

    private List<BaseZnzBean> typeList = new ArrayList<>();
    private TypeHorizontalAdapter typeAdapter;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_home, 2};
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

        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜索");
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
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_home, null);
        adapter.addHeaderView(header);

        llMore = bindViewById(header, R.id.llMore);
        rvType = bindViewById(header, R.id.rvType);

        llMore.setOnClickListener(v -> {
            gotoActivity(TypeListAct.class);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(linearLayoutManager);

        typeAdapter = new TypeHorizontalAdapter(typeList);
        rvType.setAdapter(typeAdapter);

        mBanner = bindViewById(header, R.id.mBanner);

        mBanner.setAdapter((banner, itemView, model, position) -> {

        });


//        mBanner.setDelegate((banner, itemView, model, position) -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("url", advList.get(position).getUrl());
//            bundle.putString("title", advList.get(position).getName());
//            gotoActivity(WebViewAct.class, bundle);
//        });


        List<String> advUrls = new ArrayList<>();
        List<String> advTitles = new ArrayList<>();

        mBanner.setData(R.mipmap.banner);
//        mBanner.setData(advUrls, advTitles);
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
