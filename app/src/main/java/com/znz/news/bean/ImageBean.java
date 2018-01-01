package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

/**
 * Date： 2017/12/28 2017
 * User： PSuiyi
 * Description：
 */

public class ImageBean extends BaseZnzBean {
    private String url;
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
