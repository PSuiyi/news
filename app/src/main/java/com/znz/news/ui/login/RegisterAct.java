package com.znz.news.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
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
import com.znz.news.ui.common.AgreementAct;

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

public class RegisterAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etUserName)
    EditTextWithDel etUserName;
    @Bind(R.id.etPsd)
    EditTextWithDel etPsd;
    @Bind(R.id.tvCode)
    TextView tvCode;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.tvAgreement)
    TextView tvAgreement;

    private CountDownTimer timer;
    private boolean isClickCode;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_register};
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

    @OnClick({R.id.tvCode, R.id.tvSubmit, R.id.tvAgreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCode:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }

                isClickCode = true;

                Map<String, String> params = new HashMap<>();
                params.put("mobile", mDataManager.getValueFromView(etUserName));
                mModel.requestCode(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                        timer = new CountDownTimer(60000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                tvCode.setClickable(false);
                                tvCode.setText(millisUntilFinished / 1000 + "秒");
                            }

                            public void onFinish() {
                                tvCode.setText("重新发送");
                                tvCode.setClickable(true);
                            }
                        };
                        timer.start();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
            case R.id.tvSubmit:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }
                if (!isClickCode) {
                    mDataManager.showToast("请点击获取验证码");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
                    mDataManager.showToast("请输入验证码");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone", mDataManager.getValueFromView(etUserName));
                bundle.putString("code", mDataManager.getValueFromView(etPsd));
                bundle.putString("page", "设置密码");
                gotoActivity(PsdSettingAct.class, bundle);
                break;
            case R.id.tvAgreement:
                gotoActivity(AgreementAct.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

}
