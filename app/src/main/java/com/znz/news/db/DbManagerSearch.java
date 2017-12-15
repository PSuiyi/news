package com.znz.news.db;

import android.content.Context;

import com.socks.library.KLog;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.news.bean.SearchHistoryBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project_Name： builder_master
 * Date： 2017/1/19 2017
 * User： PSuiyi
 * Description：
 */
public class DbManagerSearch {
    private static DbManagerSearch instance;
    private final Context context;
    private final DataManager dataManager;
    private SearchDao dao;

    public DbManagerSearch(Context context) {
        this.context = context;
        dataManager = DataManager.getInstance(context);
        dao = new SearchDao(context);
    }

    public static synchronized DbManagerSearch getInstance(Context mContext) {
        if (instance == null) {
            instance = new DbManagerSearch(mContext.getApplicationContext());
        }
        return instance;
    }

    /**
     * 添加列表到数据库
     *
     * @param beanList
     */
    public void addListToDB(List<SearchHistoryBean> beanList) {
        try {
            dao.deleteAll();
            dao.inserList(beanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有列表
     *
     * @return
     */
    public List<SearchHistoryBean> queryListFromDB() {
        List<SearchHistoryBean> result = new ArrayList<>();
        try {
            result = dao.queryAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 添加单个对象到数据库
     *
     * @param bean
     * @return
     */
    public boolean addSingleToDB(SearchHistoryBean bean) {
        try {
            if (!isExist(bean.getName())) {
                int result = dao.save(bean);
                if (result == 0) {
                    KLog.e("存储失败");
                    return false;
                } else {
                    KLog.e("存储成功");
                    return true;
                }
            } else {
                return updateSingleToDB(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新单个对象
     *
     * @param bean
     * @return
     */
    public boolean updateSingleToDB(SearchHistoryBean bean) {
        if (isExist(bean.getName())) {
            int result = dao.update(bean);
            if (result == 0) {
                KLog.e("更新失败");
                return false;
            } else {
                KLog.e("更新成功");
                return true;
            }
        }
        return false;
    }

    /**
     * 删除当个对象
     *
     * @param id
     * @return
     */
    public boolean deleteSingleToDB(String id) {
        try {
            if (isExist(id)) {
                int result = dao.deleteById("name", id);
                if (result == 0) {
                    KLog.e("删除失败");
                    return false;
                } else {
                    KLog.e("删除成功");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断对象是否存在
     *
     * @param id
     * @return
     */
    public boolean isExist(String id) {
        try {
            return dao.isExist("name", id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
