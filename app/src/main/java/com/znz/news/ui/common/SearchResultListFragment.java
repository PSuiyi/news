package com.znz.news.ui.common;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.adapter.MultiAdapter;
import com.znz.news.base.BaseAppListFragment;
import com.znz.news.bean.MultiBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.common.Constants;
import com.znz.news.event.EventList;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/5/23 2017
 * User： PSuiyi
 * Description：
 */

public class SearchResultListFragment extends BaseAppListFragment<MultiBean> {
    @Bind(R.id.rvCommonRefresh)
    RecyclerView rvCommonRefresh;
    private String searchContent;

    private List<NewsBean> newsBeanList = new ArrayList<>();
    private boolean mFull;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout, 1};
    }

    public static SearchResultListFragment newinstance(String searchContent) {
        Bundle args = new Bundle();
        args.putString("searchContent", searchContent);
        SearchResultListFragment listFragment = new SearchResultListFragment();
        listFragment.setArguments(args);
        return listFragment;
    }

    @Override
    protected void initializeVariate() {
        if (getArguments() != null) {
            searchContent = getArguments().getString("searchContent");
        }
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
    }

    @Override
    protected void loadDataFromServer() {
    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("key", searchContent);
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.SearchType.SEARCHTYPE))) {
            if (!mDataManager.readTempData(Constants.SearchType.SEARCHTYPE).equals("-1")) {
                params.put("contentType", mDataManager.readTempData(Constants.SearchType.SEARCHTYPE));
            }
        }
        return mModel.requestSearchList(params);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        switch (event.getFlag()) {
            case EventTags.REFRESH_SEARCH_VALUE:
                searchContent = event.getValue();
                hideNoData();
                resetRefresh();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventList event) {
        if (event.getFlag() == EventTags.LIST_COMMENT || event.getFlag() == EventTags.LIST_COMMENT_DETAIL) {
            for (MultiBean multiBean : dataList) {
                if (multiBean.getNewsBean() != null) {
                    if (multiBean.getNewsBean().getContentId().equals(event.getValue())) {
                        multiBean.getNewsBean().setEvaluateNum(StringUtil.getNumUP(multiBean.getNewsBean().getEvaluateNum()));
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
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
}
