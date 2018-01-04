package com.znz.news.event;

/**
 * Created by xxc on 2017/2/14.
 */

public interface EventTags {
    int REFRESH_SEARCH_VALUE = 0x100;
    int REFRESH_SEARCH_HISTORY = 0x101;//搜索历史记录刷新
    int REFRESH_EDIT_VALUE = 0x102;
    int REFRESH_FAV = 0x103;

    int GOTO_SHOP = 0x200;
    int GOTO_POST = 0x201;
    int GOTO_TREEMAP = 0x202;
    int GOTO_SELECT_CITY = 0x203;
    int GOTO_MINE_INFO_CITY = 0x204;
    int GOTO_HOME_CITY = 0x205;
    int GOTO_EXCHANGE_CITY = 0x206;

    int LIST_COMMENT_DETAIL = 0x300;
    int LIST_COMMENT = 0x301;
}
