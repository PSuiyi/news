package com.znz.news.common;

/**
 * Created by Administrator on 2017/12/13.
 */


public class Constants {

    /**
     * SharePreference key
     */
    public interface User {
        String ID = "id";//用户id
        String BIRTHDAY = "birthday";//出生日期
        String HEAD_IMG_PATH = "head_img_path";//头像地址
        String CITY_NAME = "city_name";//城市名称
        String IS_SGIN = "is_sgin";//APP是否签到 1签到 2未签到
        String PHONE = "phone";//手机号码
        String INV_CODE = "inv_code";//邀请码
        String INTEGRAL = "integral";//积分
        String NICK_NAME = "nick_name";//昵称
        String SEX = "sex";//性别
        String CITY_CODE = "city_code";//城市CODE
        String DATA_STATE = "data_state";//资料状态 1未完善 2已完善
        String Latitude = "Latitude";
        String Longitude = "Longitude";
        String CITY = "city";
        String CITY_DINGWEI = "CITY_DINGWEI";
        String PROVINCE_DINGWEI = "PROVINCE_DINGWEI";
        String AREA_DINGWEI = "AREA_DINGWEI";
        String STREET_DINGWEI = "STREET_DINGWEI";
        String CITY_CODE_DINGWEI = "CITY_CODE_DINGWEI";
        String AREA_CODE_DINGWEI = "AREA_CODE_DINGWEI";
        String PROVINCE_CODE_DINGWEI = "PROVINCE_CODE_DINGWEI";
        String CODE = "code";
        String PARENT_CODE = "parent_code";
    }


    /**
     * 输入框输入类型
     */
    public interface EditInputType {
        int NORMAL = 0x00001;
        int PHONE = 0x00002;
        int MULTI = 0x00003;
        int NUMBER = 0x00004;
    }

    public interface BooleanValue {
    }

    public interface AppInfo {
        String CACHE_SIZE = "CACHE_SIZE";//缓存大小
        String SPLASH_IMG_URL = "SPLASH_IMG_URL";//闪屏广告地址
    }

    public interface MultiType {
        int Section = 0;
        int Article = 2;
        int Video = 3;
        int Picture = 4;
        int Top = 5;
    }

    public interface TreeType {
        int TreeDetail = 0;
        int FeedbackList = 1;

    }

    public interface SearchType {
        String SEARCHTYPE = "SearchType";
    }

    public static final String IMAGE_CONNER = "?roundPic/radius/!50p";

}
