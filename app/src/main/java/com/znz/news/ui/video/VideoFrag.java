package com.znz.news.ui.video;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.libvideo.videoplayer.GSYVideoManager;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.VideoAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.NewsBean;
import com.znz.news.ui.common.SearchCommonActivity;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class VideoFrag extends BaseAppListFragment<NewsBean> {
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
            gotoActivity(SearchCommonActivity.class);
        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new VideoAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvRefresh.getLayoutManager();
        rvRefresh.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals("11")
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
//                        //如果滑出去了上面和下面就是否，和今日头条一样
//                        //是否全屏
//                        if (!mFull) {
//
//                        }

                        GSYVideoPlayer.releaseAllVideos();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestNewsList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("lists"), NewsBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
