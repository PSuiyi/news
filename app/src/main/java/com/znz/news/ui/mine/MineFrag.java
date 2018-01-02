package com.znz.news.ui.mine;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.rowview.ZnzRowDescription;
import com.znz.compass.znzlibray.views.rowview.ZnzRowGroupView;
import com.znz.news.R;
import com.znz.news.base.BaseAppFragment;
import com.znz.news.common.Constants;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;
import com.znz.news.ui.setting.HelpAct;
import com.znz.news.ui.setting.SettingAct;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class MineFrag extends BaseAppFragment {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.commonRowGroup)
    ZnzRowGroupView commonRowGroup;
    @Bind(R.id.llMineInfo)
    LinearLayout llMineInfo;
    @Bind(R.id.ivUserHeader)
    HttpImageView ivUserHeader;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.tvRemark)
    TextView tvRemark;
    private ArrayList<ZnzRowDescription> rowDescriptionList;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_mine};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {
        mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NICK_NAME), "暂无昵称");
        mDataManager.setValueToView(tvRemark, mDataManager.readTempData(Constants.User.REMARK), "暂无签名");
        ivUserHeader.loadHeaderImage(mDataManager.readTempData(Constants.User.HEAD_IMG_PATH));

        rowDescriptionList = new ArrayList<>();
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("我的收藏")
                .withIconResId(R.mipmap.wodeshoucang)
                .withEnableLongLine(true)
                .withEnableArraw(true)
                .withValue("0")
                .withOnClickListener(v -> {
                    gotoActivity(MineFavAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("操作帮助")
                .withIconResId(R.mipmap.wenti)
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    gotoActivity(HelpAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("系统设置")
                .withIconResId(R.mipmap.shezhi)
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    gotoActivity(SettingAct.class);
                })
                .build());
        commonRowGroup.notifyDataChanged(rowDescriptionList);
    }

    @Override
    protected void loadDataFromServer() {
        requestFavCount();
    }

    private void requestFavCount() {
        Map<String, String> params = new HashMap<>();
        mModel.requestFavCount(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                rowDescriptionList.get(0).setValue(responseOriginal.getString("data"));
                commonRowGroup.notifyDataChanged(rowDescriptionList);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @OnClick(R.id.llMineInfo)
    public void onViewClicked() {
        gotoActivity(MineInfoAct.class);
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

        if (event.getFlag() == EventTags.REFRESH_FAV) {
            requestFavCount();
        }
    }
}
