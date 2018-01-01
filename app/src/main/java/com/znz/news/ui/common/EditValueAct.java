package com.znz.news.ui.common;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithLimit;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.common.Constants;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/12/24 2017
 * User： PSuiyi
 * Description：
 */

public class EditValueAct extends BaseAppActivity {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etValue)
    EditText etValue;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etValueMulti)
    EditTextWithLimit etValueMulti;
    private String type;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_edit_value, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName(type);
    }

    @Override
    protected void initializeView() {
        switch (type) {
            case "修改昵称":
                mDataManager.setViewVisibility(etValue, true);
                break;
            case "修改签名":
                mDataManager.setViewVisibility(etValueMulti, true);
                break;
        }

        mDataManager.toggleInputSoft();
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        Map<String, String> params = new HashMap<>();
        switch (type) {
            case "修改昵称":
                if (StringUtil.isBlank(mDataManager.getValueFromView(etValue))) {
                    mDataManager.showToast("请输入昵称");
                    return;
                }
                params.put("nickname", mDataManager.getValueFromView(etValue));
                mModel.requestUpdateNickname(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("修改成功");
                        mDataManager.saveTempData(Constants.User.NICK_NAME, mDataManager.getValueFromView(etValue));
                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_EDIT_VALUE));
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });

                break;
            case "修改签名":
                if (StringUtil.isBlank(mDataManager.getValueFromView(etValueMulti))) {
                    mDataManager.showToast("请输入签名");
                    return;
                }
                params.put("profile", mDataManager.getValueFromView(etValueMulti));
                mModel.requestUpdateRemark(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("修改成功");
                        mDataManager.saveTempData(Constants.User.REMARK, mDataManager.getValueFromView(etValueMulti));
                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_EDIT_VALUE));
                        finish();
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
