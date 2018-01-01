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

    public void requestCateTreeList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCateTreeList(params), znzHttpListener);
    }

    public Observable<ResponseBody> requestNewsList(Map<String, String> params) {
        return apiService.requestNewsList(params);
    }

    public Observable<ResponseBody> requestCommentList(Map<String, String> params) {
        return apiService.requestCommentList(params);
    }

    public Observable<ResponseBody> requestFavList(Map<String, String> params) {
        return apiService.requestFavList(params);
    }

    public Observable<ResponseBody> requestSearchList(Map<String, String> params) {
        return apiService.requestSearchList(params);
    }

    public void requestNewsList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestNewsList(params), znzHttpListener);
    }

    public void requestCommentAdd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCommentAdd(params), znzHttpListener, LODING_PD);
    }

    public void requestFavCount(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestFavCount(params), znzHttpListener);
    }

    public void requestFavAdd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestFavAdd(params), znzHttpListener);
    }

    public void requestUpdateNickname(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdateNickname(params), znzHttpListener, LODING_PD);
    }

    public void requestUpdatePsd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdatePsd(params), znzHttpListener, LODING_PD);
    }

    public void requestUpdateRemark(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdateRemark(params), znzHttpListener, LODING_PD);
    }
}
