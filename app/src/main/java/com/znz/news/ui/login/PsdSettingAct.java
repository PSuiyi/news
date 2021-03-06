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
import com.znz.news.ui.TabHomeAct;

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

public class PsdSettingAct extends BaseAppActivity {
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
    @Bind(R.id.etPsdNew)
    EditTextWithDel etPsdNew;
    private String phone;
    private String code;
    private String page;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_psd_setting, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("page")) {
            page = getIntent().getStringExtra("page");
        }
        if (getIntent().hasExtra("code")) {
            code = getIntent().getStringExtra("code");
        }
        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("设置密码");
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdNew))) {
            mDataManager.showToast("请输入新密码");
            return;
        }
        if (!mDataManager.getValueFromView(etPsd).equals(mDataManager.getValueFromView(etPsdNew))) {
            mDataManager.showToast("密码输入不一致");
            return;
        }

        Map<String, String> params = new HashMap<>();
        switch (page) {
            case "修改密码":
                params.put("password", mDataManager.getValueFromView(etPsd));
                params.put("confirmPassword", mDataManager.getValueFromView(etPsdNew));
                mModel.requestUpdatePsd(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("修改成功");
                        gotoActivityWithClearStack(TabHomeAct.class);
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
            case "找回密码":
                params.put("code", code);
                params.put("mobile", phone);
                params.put("password", mDataManager.getValueFromView(etPsd));
                params.put("confirmPassword", mDataManager.getValueFromView(etPsdNew));
                mModel.requestForgetPsd(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("修改成功");
                        gotoActivityWithClearStack(LoginAct.class);
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
            default:
                params.put("password", mDataManager.getValueFromView(etPsd));
                params.put("confirmPassword", mDataManager.getValueFromView(etPsdNew));
                params.put("code", code);
                params.put("mobile", phone);
                mModel.requestRegister(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("注册成功");
                        gotoActivityWithClearStack(LoginAct.class);
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
