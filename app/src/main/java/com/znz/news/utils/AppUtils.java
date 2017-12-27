package com.znz.news.utils;

import android.content.Context;

import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.news.common.Constants;
import com.znz.news.bean.UserBean;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class AppUtils {

    private DataManager mDataManager;
    private static AppUtils instance;
    private Context context;

    public static AppUtils getInstance(Context context) {
        if (instance == null) {
            instance = new AppUtils(context.getApplicationContext());
        }
        return instance;
    }

    private AppUtils(Context context) {
        mDataManager = DataManager.getInstance(context.getApplicationContext());
    }

    /**
     * 保存用户信息
     *
     * @param bean
     */
    public void saveUserData(UserBean bean) {
//        mDataManager.saveTempData(Constants.User.ID, bean.getUser_id());
//        mDataManager.saveTempData(Constants.User.PHONE, bean.getPhone());
//        mDataManager.saveTempData(Constants.User.NICK_NAME, bean.getNick_name());
//        mDataManager.saveTempData(Constants.User.HEAD_IMG_PATH, bean.getHead_img_path());
//        mDataManager.saveTempData(Constants.User.BIRTHDAY, bean.getBirthday());
//        mDataManager.saveTempData(Constants.User.CITY_NAME, bean.getCity_name());
//        mDataManager.saveTempData(Constants.User.IS_SGIN, bean.getIs_sgin());
//        mDataManager.saveTempData(Constants.User.INV_CODE, bean.getInv_code());
//        mDataManager.saveTempData(Constants.User.INTEGRAL, bean.getIntegral());
//        mDataManager.saveTempData(Constants.User.SEX, bean.getSex());
//        mDataManager.saveTempData(Constants.User.CITY_CODE, bean.getCity_code());
//        mDataManager.saveTempData(Constants.User.DATA_STATE, bean.getData_state());
    }


    /**
     * 获取昵称
     *
     * @return
     */
    public String getNickName() {
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.NICK_NAME))) {
            return mDataManager.readTempData(Constants.User.NICK_NAME);
        } else {
            return mDataManager.readTempData(Constants.User.PHONE);
        }
    }

    /**
     * 获取昵称
     *
     * @param userBean
     * @return
     */
//    public String getNickName(UserBean userBean) {
//        if (userBean == null) {
//            return "游客";
//        }
//        if (!StringUtil.isBlank(userBean.getNick_name())) {
//            return userBean.getNick_name();
//        } else {
//            if (!StringUtil.isBlank(userBean.getPhone())) {
//                return userBean.getPhone();
//            } else {
//                return "游客";
//            }
//        }
//    }
}
