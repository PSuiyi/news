package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

import java.util.List;

/**
 * Date： 2017/12/28 2017
 * User： PSuiyi
 * Description：
 */

public class CateBean extends BaseZnzBean {

    /**
     * cateDesc : 好的
     * cateId : 21
     * cateLevel : 1
     * cateName : 新浪娱乐1
     * catePid : 0
     */

    private String cateDesc;
    private String cateId;
    private String cateLevel;
    private String cateName;
    private String catePid;
    private List<CateBean> subCate;

    public List<CateBean> getSubCate() {
        return subCate;
    }

    public void setSubCate(List<CateBean> subCate) {
        this.subCate = subCate;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(String cateLevel) {
        this.cateLevel = cateLevel;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCatePid() {
        return catePid;
    }

    public void setCatePid(String catePid) {
        this.catePid = catePid;
    }
}
