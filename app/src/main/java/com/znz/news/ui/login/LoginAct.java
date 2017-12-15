package com.znz.news.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.ui.TabHomeAct;

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
    @Bind(R.id.tvCode)
    TextView tvCode;
    @Bind(R.id.tvLogin)
    TextView tvLogin;
    @Bind(R.id.tvAgreement)
    TextView tvAgreement;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_login};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {

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

    @OnClick({R.id.tvCode, R.id.tvLogin, R.id.tvAgreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCode:
                break;
            case R.id.tvLogin:
                gotoActivity(TabHomeAct.class);
                break;
            case R.id.tvAgreement:
                break;
        }
    }
}
