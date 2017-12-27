package com.znz.news.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
}
