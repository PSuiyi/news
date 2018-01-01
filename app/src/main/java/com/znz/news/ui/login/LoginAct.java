package com.znz.news.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.common.ZnzConstants;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.bean.UserBean;
import com.znz.news.ui.TabHomeAct;
import com.znz.news.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class LoginAct extends BaseAppActivity {
    @Bind(R.id.etUserName)
    EditTextWithDel etUserName;
    @Bind(R.id.etPsd)
    EditTextWithDel etPsd;
    @Bind(R.id.tvLogin)
    TextView tvLogin;
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvRegister)
    TextView tvRegister;
    @Bind(R.id.tvForget)
    TextView tvForget;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_login};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(ZnzConstants.ACCOUNT))) {
            etUserName.setText(mDataManager.readTempData(ZnzConstants.ACCOUNT));
        }
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

    @OnClick({R.id.tvLogin, R.id.tvRegister, R.id.tvForget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
                    mDataManager.showToast("请输入密码");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mDataManager.getValueFromView(etUserName));
                params.put("password", mDataManager.getValueFromView(etPsd));
                mModel.requestLogin(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        UserBean bean = JSON.parseObject(responseOriginal.getString("data"), UserBean.class);
                        mDataManager.saveBooleanTempData(ZnzConstants.IS_LOGIN, true);
                        mDataManager.saveTempData(ZnzConstants.ACCESS_TOKEN, bean.getToken());
                        AppUtils.getInstance(context).saveUserData(bean);
                        gotoActivity(TabHomeAct.class);
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
            case R.id.tvRegister:
                gotoActivity(RegisterAct.class);
                break;
            case R.id.tvForget:
                gotoActivity(AuthAct.class);
                break;
        }
    }
}
