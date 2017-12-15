package com.znz.compass.znzlibray;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.common.ZnzConstants;
import com.znz.compass.znzlibray.utils.ForegroundCallbacks;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;

/**
 * 应用全局变量
 */
public class ZnzApplication extends MultiDexApplication {
    /**
     * Singleton pattern
     */
    private static ZnzApplication instance;
    private static Context context;
    protected DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = DataManager.getInstance(this);
        context = this;

        //设置网络访问请求链接
        if (StringUtil.isBlank(dataManager.readTempData(ZnzConstants.SERVICE_IP))) {
            if (ZnzConstants.APP_DEBUG) {
                dataManager.saveTempData(ZnzConstants.SERVICE_IP, "http://syapi.znzkj.net/");//测试环境
            } else {
                dataManager.saveTempData(ZnzConstants.SERVICE_IP, "http://23.236.76.7:8083/");//正式环境
            }
//            dataManager.saveTempData(ZnzConstants.SERVICE_IP, "http://10.40.253.203/");//本地环境
//            dataManager.saveTempData(ZnzConstants.SERVICE_IP, "http://tyapi.znzkj.net/");//测试环境
        }

        //设置前后台监听
        ForegroundCallbacks.init(this);
        ForegroundCallbacks.get().addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecomeForeground() {
                ZnzLog.d("当前程序切换到前台");
            }

            @Override
            public void onBecomeBackground() {
                ZnzLog.d("当前程序切换到后台");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static ZnzApplication getInstance() {
        synchronized (ZnzApplication.class) {
            if (instance == null) {
                instance = new ZnzApplication();
            }
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }
}
