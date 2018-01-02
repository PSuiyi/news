package com.znz.news.ui.picture;

import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.CommentAdapter;
import com.znz.news.base.BaseAppListActivity;
import com.znz.news.bean.CommentBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class CommentListAct extends BaseAppListActivity<CommentBean> implements View.OnLayoutChangeListener {

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
    @Bind(R.id.tvSend)
    TextView tvSend;
    @Bind(R.id.llContainer)
    LinearLayout llContainer;
    @Bind(R.id.tvComment)
    TextView tvComment;
    @Bind(R.id.llComment1)
    LinearLayout llComment1;
    @Bind(R.id.llComment2)
    LinearLayout llComment2;
    private String id;

    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_comment_list, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("评论");
        setNavLeftGone();
        znzToolBar.setNavLeft(R.mipmap.guanbi);
        znzToolBar.setOnNavLeftClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new CommentAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        llContainer.addOnLayoutChangeListener(this);

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    @Override
    protected void loadDataFromServer() {

    }


    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("contentId", id);
        return mModel.requestCommentList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), CommentBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mDataManager.getDeviceHeight(activity) / 4)) {
            ZnzLog.e("监听到软键盘---->" + "弹起....");
            runOnUiThread(() -> {
                mDataManager.setViewVisibility(llComment1, false);
                mDataManager.setViewVisibility(llComment2, true);
            });
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mDataManager.getDeviceHeight(activity) / 4)) {
            ZnzLog.e("监听到软键盘---->" + "关闭....");
            runOnUiThread(() -> {
                mDataManager.setViewVisibility(llComment1, true);
                mDataManager.setViewVisibility(llComment2, false);
            });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @OnClick({R.id.tvComment, R.id.tvSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvComment:
                mDataManager.setViewVisibility(llComment1, false);
                mDataManager.setViewVisibility(llComment2, true);
                mDataManager.toggleEditTextFocus(etComment, true);
                break;
            case R.id.tvSend:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etComment))) {
                    mDataManager.showToast("请输入评论内容");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("contentId", id);
                params.put("EContent", mDataManager.getValueFromView(etComment));
                mModel.requestCommentAdd(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("评论成功");
                        etComment.setText("");
                        mDataManager.setViewVisibility(llComment1, true);
                        mDataManager.setViewVisibility(llComment2, false);
                        resetRefresh();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
        }
    }
}
