package com.znz.news.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("auth/vercode")
    Observable<ResponseBody> requestCode(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/register")
    Observable<ResponseBody> requestRegister(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/login")
    Observable<ResponseBody> requestLogin(@FieldMap Map<String, String> params);

    @GET("content/list")
    Observable<ResponseBody> requestNewsList(@QueryMap Map<String, String> params);

    @GET("comment/list")
    Observable<ResponseBody> requestCommentList(@QueryMap Map<String, String> params);

    @GET("content/detail")
    Observable<ResponseBody> requestNewsDetail(@QueryMap Map<String, String> params);

    @GET("banner/list")
    Observable<ResponseBody> requestBannerList(@QueryMap Map<String, String> params);

    @GET("category/top")
    Observable<ResponseBody> requestCateOneList(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("user/verify/password")
    Observable<ResponseBody> requestCheckPsd(@FieldMap Map<String, String> params);
}
