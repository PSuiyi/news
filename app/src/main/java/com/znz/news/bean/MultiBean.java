package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.entity.MultiItemEntity;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiBean extends BaseZnzBean implements MultiItemEntity {
    private int itemType;
    private String section;
    private NewsBean newsBean;

    public MultiBean() {
    }

    public MultiBean(int itemType) {
        this.itemType = itemType;
    }


    public MultiBean(int itemType, NewsBean newsBean) {
        this.itemType = itemType;
        this.newsBean = newsBean;
    }


    public MultiBean(int itemType, String section) {
        this.itemType = itemType;
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
