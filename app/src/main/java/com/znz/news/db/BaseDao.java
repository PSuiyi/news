package com.znz.news.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.znz.compass.znzlibray.utils.ZnzLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDao<T, Integer> {
    private static final String TAG = "DatabaseHelper-BaseDao";

    protected DatabaseHelper mDatabaseHelper;
    protected Context mContext;

    public BaseDao(Context context) {
        mContext = context;
        getHelper();
    }

    public DatabaseHelper getHelper() {
        if (mDatabaseHelper == null) {
            // mDatabaseHelper = OpenHelperManager.getHelper(mContext,
            // DatabaseHelper.class);
            mDatabaseHelper = DatabaseHelper.getHelper(mContext);

        }
        return mDatabaseHelper;
    }

    public abstract Dao<T, Integer> getDao() throws SQLException;

    /**
     * 返回主键描述
     *
     * @return
     */
    public abstract String getPrimaryKeyDescription();

    /**
     * 插入对象到数据库 t 对象
     */
    public int save(T t) {
        try {
            return getDao().create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 插入list 到数据库
     */
    public void inserList(List<T> list) {
        try {
            for (T Tbean : list) {
                getDao().createOrUpdate(Tbean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.query(preparedQuery);
    }

    /**
     * 多条件查询 返回 list
     */
    public List<T> query(String attributeName, String attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    /**
     * 多条件修改
     */
    public void update(String attributeName, String attributeValue) throws SQLException {
        UpdateBuilder<T, Integer> updateBuilder = getDao().updateBuilder();
        updateBuilder.where().eq(attributeName, attributeValue);
        PreparedUpdate<T> preparedUpdate = updateBuilder.prepare();
        getDao().update(preparedUpdate);
    }

    /**
     * 多条件查询 返回 list
     */
    public List<T> queryCity(String attributeName, String attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().like(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }


    /**
     * 多条件查询 返回 list
     */
    public List<T> query(String[] attributeNames, Object[] attributeValues) {
        PreparedQuery<T> preparedQuery = null;
        try {
            if (attributeNames.length != attributeValues.length) {
                throw new Exception("params size is not equal");
            }
            QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
//			Where<T, Integer> wheres = queryBuilder.where();
//			for (int i = 0; i < attributeNames.length; i++) {
//				queryBuilder.where().eq(attributeNames[i], attributeValues[i]);
//			}
            queryBuilder.where().eq(attributeNames[0], attributeValues[0]).and().eq(attributeNames[1], attributeValues[1]);
            preparedQuery = queryBuilder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件查询 返回 list
     */
    public List<T> queryAll() {
        // QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        // PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        // return query(preparedQuery);
        try {
            Dao<T, Integer> dao = getDao();
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            ZnzLog.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * 单条件查询 T 返回对象
     */
    public T queryById(String idName, String idValue) {
        try {
            List<T> lst = query(idName, idValue);
            if (null != lst && !lst.isEmpty()) {
                return lst.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(preparedDelete);
    }

    /**
     * 删除对象 t 对象
     */
    public int delete(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(t);
    }

    /**
     * 删除 list
     */
    public int delete(List<T> list) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(list);

    }

    /**
     * 删除全部
     */
    public void deleteAll() throws SQLException {
        Dao<T, Integer> dao = getDao();
        dao.delete(dao.queryForAll());
    }

    /**
     *
     * String attributeNames 表的字段
     *
     * String attributeValues 参数
     *
     * */
//	public int delete(String[] attributeNames, String[] attributeValues) throws SQLException, Exception {
//		List<T> lst = query(attributeNames, attributeValues);
//		if (null != lst && !lst.isEmpty()) {
//			return delete(lst);
//		}
//		return 0;
//	}

    /**
     * String idName 表的字段
     * <p>
     * String idValue 参数
     */
    public int deleteById(String idName, String idValue) throws SQLException, Exception {
        T t = queryById(idName, idValue);
        if (null != t) {
            return delete(t);
        }
        return 0;
    }

    /**
     * update 更新数据
     */
    public int update(T t) {
        try {
            Dao<T, Integer> dao = getDao();
            return dao.update(t);
        } catch (Exception e) {
            e.printStackTrace();
            ZnzLog.e(TAG, e.toString());
        }
        return 0;
    }

    /**
     * update 更新数据
     */
    public void createOrUpdate(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        dao.createOrUpdate(t);
    }

    /**
     * 判断表是否存在
     */
    public boolean isTableExsits() throws SQLException {
        return getDao().isTableExists();
    }

    /**
     * @return
     * @throws SQLException
     */
    public long countOf() throws SQLException {
        return getDao().countOf();
    }

    /**
     * 查询对象并排序 Map<String, Object> String 表字段 Object 参数 (多条件查询) 一个条件
     */
    public List<T> query(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (!map.isEmpty()) {
            Where<T, Integer> wheres = queryBuilder.where();
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    /**
     * 查询对象并排序 Map<String, Object> String 表字段 Object 参数 (多条件查询)
     * <p>
     * 多条件
     */
    public List<T> query(Map<String, Object> map, Map<String, Object> lowMap, Map<String, Object> highMap)
            throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        if (!map.isEmpty()) {
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        if (!lowMap.isEmpty()) {
            Set<String> keys = lowMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (map.isEmpty()) {
                    wheres.gt(keyss.get(i), lowMap.get(keyss.get(i)));
                } else {
                    wheres.and().gt(keyss.get(i), lowMap.get(keyss.get(i)));
                }
            }
        }

        if (!highMap.isEmpty()) {
            Set<String> keys = highMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                wheres.and().lt(keyss.get(i), highMap.get(keyss.get(i)));
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    /**
     * 查询对象并排序 String 表字段 Object 参数
     * <p>
     * sortName参数 排序字段
     */
    public T querySignle(String sortName) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();

        queryBuilder.orderBy(sortName, false);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery).get(0);
    }

    /**
     * 查询对象并排序 Map<String, Object> String 表字段 Object 参数 (多条件查询)
     * <p>
     * 多条件
     */
    public T querySignle(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        if (!map.isEmpty()) {
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        queryBuilder.orderBy("datetime", false);
        queryBuilder.limit(1);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery).get(0);
    }

    public T queryId(String param) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.orderBy(param, false);
        queryBuilder.limit(1);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();

        if (query(preparedQuery).size() == 0) {
            return null;
        }
        return query(preparedQuery).get(0);
    }

    public T queryCityId(String param, String id) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq("user_id", id);
        queryBuilder.orderBy(param, false);
        queryBuilder.limit(1);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();

        if (query(preparedQuery).size() == 0) {
            return null;
        }
        return query(preparedQuery).get(0);
    }

    /**
     * 多条件查询 返回 list
     */
    public T querySignle(String[] attributeNames, String[] attributeValues) throws SQLException, Exception {
        if (attributeNames.length != attributeValues.length) {
            throw new Exception("params size is not equal");
        }
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        for (int i = 0; i < attributeNames.length; i++) {
            wheres.eq(attributeNames[i], attributeValues[i]);
        }
        queryBuilder.orderBy("datetime", false);
        queryBuilder.limit(1);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery).get(0);
    }

    public GenericRawResults<String[]> queryRaw(String sql) throws InterruptedException {
        try {
            return getDao().queryRaw(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据条件判断数据库中是否存在这条数据
     *
     * @param column 字段
     */
    public boolean isExist(String column, String word) throws SQLException {
        QueryBuilder<T, Integer> builder = getDao().queryBuilder();
        builder.where().eq(column, word);
        PreparedQuery<T> preparedQuery = builder.prepare();
        if (query(preparedQuery).size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id 查询该条是否存在
     *
     * @param key
     * @param key
     */
    public boolean isExistId(String key, int value) throws SQLException {
        QueryBuilder<T, Integer> builder = getDao().queryBuilder();
        builder.where().eq(key, value);
        PreparedQuery<T> preparedQuery = builder.prepare();
        if (query(preparedQuery).size() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
