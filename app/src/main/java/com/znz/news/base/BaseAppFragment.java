package com.znz.news.base;

import com.znz.compass.znzlibray.base.BaseFragment;
import com.znz.news.api.ApiModel;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public abstract class BaseAppFragment extends BaseFragment {
    protected ApiModel mModel;

    @Override
    protected void initializeAppBusiness() {
        mModel = new ApiModel(activity, this);
    }

}
