package com.znz.news.api;

import android.content.Context;

import com.znz.compass.znzlibray.base_znz.BaseModel;
import com.znz.compass.znzlibray.base_znz.IView;
import com.znz.compass.znzlibray.network.retorfit.ZnzRetrofitUtil;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/12/11 2017
 * User： PSuiyi
 * Description：
 */

public class ApiModel extends BaseModel {
    private ApiService apiService;

    public ApiModel(Context context, IView mView) {
        super(context, mView);
        apiService = ZnzRetrofitUtil.getInstance().createService(ApiService.class);
    }

    public void requestCode(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCode(params), znzHttpListener, LODING_PD);
    }

    public void requestRegister(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestRegister(params), znzHttpListener, LODING_PD);
    }

    public void requestLogin(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestLogin(params), znzHttpListener, LODING_PD);
    }

    public void requestCheckPsd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCheckPsd(params), znzHttpListener, LODING_PD);
    }

    public void requestNewsDetail(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestNewsDetail(params), znzHttpListener, LODING_LODING);
    }

    public void requestBannerList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestBannerList(params), znzHttpListener);
    }

    public void requestCateOneList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCateOneList(params), znzHttpListener);
    }

    public Observable<ResponseBody> requestNewsList(Map<String, String> params) {
        return apiService.requestNewsList(params);
    }

    public void requestNewsList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestNewsList(params), znzHttpListener);
    }
}
