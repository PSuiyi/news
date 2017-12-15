package com.znz.news.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.znz.compass.znzlibray.bean.BaseZnzBean;

/**
 * Project_Name： combination
 * Date： 2017/2/13 2017
 * User： PSuiyi
 * Description：
 */
@DatabaseTable(tableName = "search_history")
public class SearchHistoryBean extends BaseZnzBean {
    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private String keyword;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
