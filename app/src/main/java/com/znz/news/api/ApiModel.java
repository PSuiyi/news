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

    public void checkPhone(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("request_code", "00011");
        request(apiService.post(params), znzHttpListener);
    }

    public Observable<ResponseBody> requestVideoList(Map<String, String> params) {
        params.put("request_code", "30000");
        return apiService.post(params);
    }

    public void requestVideoDetail(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("request_code", "30001");
        request(apiService.post(params), znzHttpListener, LODING_LODING);
    }
}
