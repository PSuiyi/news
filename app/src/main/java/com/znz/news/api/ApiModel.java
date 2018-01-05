package com.znz.news.api;

import android.content.Context;

import com.znz.compass.znzlibray.base_znz.BaseModel;
import com.znz.compass.znzlibray.base_znz.IView;
import com.znz.compass.znzlibray.network.retorfit.ZnzRetrofitUtil;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

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

    public void requestCodeForget(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestCodeForget(params), znzHttpListener, LODING_PD);
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

    public void requestVersion(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestVersion(params), znzHttpListener);
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

    public void requestFavCancel(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestFavCancel(params), znzHttpListener);
    }

    public void requestUpdateNickname(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdateNickname(params), znzHttpListener, LODING_PD);
    }

    public void requestUpdatePsd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdatePsd(params), znzHttpListener, LODING_PD);
    }

    public void requestForgetPsd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestForgetPsd(params), znzHttpListener, LODING_PD);
    }

    public void requestUpdateRemark(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdateRemark(params), znzHttpListener, LODING_PD);
    }

    public void requestUpdateHeader(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.requestUpdateHeader(params), znzHttpListener);
    }

    public void requestUploadImage(String url, ZnzHttpListener znzHttpListener) {
        Map<String, String> params = new HashMap<>();
        params.put("filetype", "1");
        params.put("dirname", "avatar");
        File file = new File(url);
        Luban.get(context)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> throwable.printStackTrace())
                .onErrorResumeNext(throwable -> Observable.empty())
                .subscribe(file1 -> {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file1);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    request(apiService.uploadImageSingle(params, body), znzHttpListener, LODING_PD);
                });
    }
}
