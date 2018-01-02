package com.znz.news.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.common.Constants;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;
import com.znz.news.ui.common.EditValueAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class MineInfoAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.ivUserHeader)
    HttpImageView ivUserHeader;
    @Bind(R.id.llUserHeader)
    LinearLayout llUserHeader;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.llNickName)
    LinearLayout llNickName;
    @Bind(R.id.llSign)
    LinearLayout llSign;
    @Bind(R.id.tvRemark)
    TextView tvRemark;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_mine_info, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("个人资料");
    }

    @Override
    protected void initializeView() {
        mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NICK_NAME), "暂无昵称");
        mDataManager.setValueToView(tvRemark, mDataManager.readTempData(Constants.User.REMARK), "暂无签名");
        ivUserHeader.loadHeaderImage(mDataManager.readTempData(Constants.User.HEAD_IMG_PATH));
    }

    @Override
    protected void loadDataFromServer() {

    }


    @OnClick({R.id.llUserHeader, R.id.llNickName, R.id.llSign})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.llUserHeader:
                mDataManager.openPhotoSelectSingle(activity, new IPhotoSelectCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> photoList) {
                        if (!photoList.isEmpty()) {
                            mModel.requestUploadImage(photoList.get(0), new ZnzHttpListener() {
                                @Override
                                public void onSuccess(JSONObject responseOriginal) {
                                    super.onSuccess(responseOriginal);
                                    ivUserHeader.loadHeaderImage(photoList.get(0));
                                    mDataManager.saveTempData(Constants.User.HEAD_IMG_PATH, photoList.get(0));
                                    EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_EDIT_VALUE));
                                }

                                @Override
                                public void onFail(String error) {
                                    super.onFail(error);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                }, true);
                break;
            case R.id.llNickName:
                bundle.putString("type", "修改昵称");
                gotoActivity(EditValueAct.class, bundle);
                break;
            case R.id.llSign:
                bundle.putString("type", "修改签名");
                gotoActivity(EditValueAct.class, bundle);
                break;
        }
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
        if (event.getFlag() == EventTags.REFRESH_EDIT_VALUE) {
            mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NICK_NAME), "暂无昵称");
            mDataManager.setValueToView(tvRemark, mDataManager.readTempData(Constants.User.REMARK), "暂无签名");
            ivUserHeader.loadHeaderImage(mDataManager.readTempData(Constants.User.HEAD_IMG_PATH));
        }
    }
}
