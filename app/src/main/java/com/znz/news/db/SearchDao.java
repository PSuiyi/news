package com.znz.news.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.znz.news.bean.SearchHistoryBean;

import java.sql.SQLException;

/**
 * Date： 2017/2/21 2017
 * User： PSuiyi
 * Description：
 */

public class SearchDao extends BaseDao {
    public SearchDao(Context context) {
        super(context);
    }

    @Override
    public Dao getDao() throws SQLException {
        return getHelper().getDao(SearchHistoryBean.class);
    }

    @Override
    public String getPrimaryKeyDescription() {
        return null;
    }
}
