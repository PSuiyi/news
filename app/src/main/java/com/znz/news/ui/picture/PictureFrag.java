package com.znz.news.ui.picture;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.news.R;
import com.znz.news.adapter.CoverFlowAdapter;
import com.znz.news.adapter.PictureAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.view.RecyclerCoverFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class PictureFrag extends BaseAppListFragment {

    private View header;
    private RecyclerCoverFlow rvCoverFlow;
    private CoverFlowAdapter coverFlowAdapter;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 2};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜您想要的图片内容");
        znzToolBar.setEnableEdit(false);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new PictureAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_picture, null);
        adapter.addHeaderView(header);
        rvCoverFlow = bindViewById(header, R.id.rvCoverFlow);

        List<BaseZnzBean> li = new ArrayList<>();
        li.add(new BaseZnzBean());
        li.add(new BaseZnzBean());
        li.add(new BaseZnzBean());
        li.add(new BaseZnzBean());
        li.add(new BaseZnzBean());
        coverFlowAdapter = new CoverFlowAdapter(li);
        rvCoverFlow.setAdapter(coverFlowAdapter);
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
