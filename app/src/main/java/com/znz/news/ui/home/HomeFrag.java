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

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFrag extends BaseAppListFragment<MultiBean> {

    private View header;
    private RecyclerView rvType;
    private LinearLayout llMore;

    private List<BaseZnzBean> typeList = new ArrayList<>();
    private TypeHorizontalAdapter typeAdapter;

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

        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
        typeList.add(new BaseZnzBean());
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
