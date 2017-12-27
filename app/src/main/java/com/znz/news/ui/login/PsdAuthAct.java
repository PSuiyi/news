package com.znz.news.ui.login;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/12/18 2017
 * User： PSuiyi
 * Description：
 */

public class PsdAuthAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etPsd)
    EditTextWithDel etPsd;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_psd_auth, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("修改密码");
    }

    @Override
    protected void initializeView() {

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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
            mDataManager.showToast("请输入密码");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("password", mDataManager.getValueFromView(etPsd));
        mModel.requestCheckPsd(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                gotoActivityWithClearStack(PsdSettingAct.class);
            }
        });
    }
}
