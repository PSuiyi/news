package com.znz.news.ui.common;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.EditTextWithLimit;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;

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
        finish();
    }
}
