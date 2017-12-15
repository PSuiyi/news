package com.znz.news.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("muying_api/s/api")
    Observable<ResponseBody> post(@FieldMap Map<String, String> params);

    @Multipart
    @POST("muying_api/s/api")
    Observable<ResponseBody> uploadImageSingle(@QueryMap Map<String, String> params, @Part MultipartBody.Part file);

    @Multipart
    @POST("muying_api/s/api")
    Observable<ResponseBody> uploadImage(@QueryMap Map<String, String> params, @Part() List<MultipartBody.Part> parts);

}
