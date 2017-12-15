package com.znz.news.event;

/**
 * Created by xxc on 2017/2/14.
 */

public interface EventTags {
    int REFRESH_SEARCH_VALUE = 0x100;
    int REFRESH_SEARCH_HISTORY = 0x101;//搜索历史记录刷新
    int REFRESH_USER_INFO = 0x104;//刷新用户信息
    int REFRESH_ACTIVITY_STATE = 0x105;//活动报名成功，修改详情界面报名状态
    int REFRESH_POST = 0x106;//发帖成功刷新帖子列表
    int REFRESH_AUDIT = 0x107;//提交约拍后刷新约拍详情界面
    int REFRESH_PRODUCT = 0x108;//树礼兑换成功通知树礼详情刷新

    int GOTO_SHOP = 0x200;
    int GOTO_POST = 0x201;
    int GOTO_TREEMAP = 0x202;
    int GOTO_SELECT_CITY = 0x203;
    int GOTO_MINE_INFO_CITY = 0x204;
    int GOTO_HOME_CITY = 0x205;
    int GOTO_EXCHANGE_CITY = 0x206;

    int LIST_POST_VIEW_COUNT = 0x300;
    int FINISH_LAST_PAGE = 0x400;//关闭上一个界面

}
