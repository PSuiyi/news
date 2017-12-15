package com.znz.news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.znz.news.R;
import com.znz.news.bean.SearchHistoryBean;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = R.string.app_name + ".db";
    private static final int DATABASE_VERSION = 2;

    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    private static DatabaseHelper helper = null;

    private DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    /**
     * //此段代码 添加数据库到指点的文件夹 public static synchronized DatabaseHelper
     * getHelper(Context context) { if (helper == null) { DataManager
     * dataManager = DataManager.getInstance(context); File file = new
     * File(dataManager.getmDataDir(), DATABASE_NAME); filePath =
     * file.getPath(); helper = new DatabaseHelper(context, file.getPath()); }
     * usageCounter.incrementAndGet(); return helper; }
     */
    /**
     * 添加数据库到默认的文件夹
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context, DATABASE_NAME);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, SearchHistoryBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, SearchHistoryBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(db, connectionSource);
    }

    /**
     * Close the database connections and clear any cached DAOs. For each call
     * to {@link #getHelper(Context)}, there should be 1 and only 1 call to this
     * method. If there were 3 calls to {@link #getHelper(Context)} then on the
     * 3rd call to this method, the helper and the underlying database
     * connections will be closed.
     */
    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            helper = null;
        }
    }
}
