package com.znz.news.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.libvideo.videoplayer.GSYVideoManager;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class MineFavAct extends BaseAppListActivity<MultiBean> {

    private List<NewsBean> newsBeanList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {
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
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestFavList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        newsBeanList.clear();
        newsBeanList.addAll(JSONArray.parseArray(responseJson.getString("list"), NewsBean.class));
        if (!newsBeanList.isEmpty()) {
            for (NewsBean newsBean : newsBeanList) {
                switch (newsBean.getContentType()) {
                    case "0":
                        dataList.add(new MultiBean(Constants.MultiType.Article, newsBean));
                        break;
                    case "1":
                        dataList.add(new MultiBean(Constants.MultiType.Picture, newsBean));
                        break;
                    case "2":
                        dataList.add(new MultiBean(Constants.MultiType.Video, newsBean));
                        break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        if (event.getFlag() == EventTags.REFRESH_FAV) {
            resetRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        EventManager.unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

}
