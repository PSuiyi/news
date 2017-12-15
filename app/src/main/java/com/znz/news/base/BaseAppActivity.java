package com.znz.news.base;

import android.os.Bundle;

import com.znz.compass.znzlibray.base.BaseActivity;
import com.znz.jobs.api.ApiModel;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public abstract class BaseAppActivity extends BaseActivity {

    protected ApiModel mModel;

    @Override
    protected void initializeAppBusiness() {
        mModel = new ApiModel(activity, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mModel != null) {
            mModel.MODestory();
        }
    }
}
