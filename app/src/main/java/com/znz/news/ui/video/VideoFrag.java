package com.znz.news.ui.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;
import com.znz.news.ui.common.SearchCommonActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class VideoFrag extends BaseAppListFragment<MultiBean> {

    private boolean mFull;
    private List<NewsBean> newsBeanList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 2};
    }

    @Override
    protected void initializeVariate() {
    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("搜您想要的视频内容");
        znzToolBar.setEnableEdit(false);
        znzToolBar.setOnSearchClickListener(v -> {
            mDataManager.saveTempData(Constants.SearchType.SEARCHTYPE, "2");
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
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("contentType", "2");
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        newsBeanList.clear();
        newsBeanList.addAll(JSONArray.parseArray(responseJson.getString("list"), NewsBean.class));
        if (!newsBeanList.isEmpty()) {
            for (NewsBean newsBean : newsBeanList) {
                dataList.add(new MultiBean(Constants.MultiType.VideoHot, newsBean));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }

    }

    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            GSYVideoPlayer.releaseAllVideos();
        }
    }
}
