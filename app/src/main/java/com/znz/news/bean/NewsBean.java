package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

import java.util.List;

/**
 * Date： 2017/12/15 2017
 * User： PSuiyi
 * Description：
 */

public class NewsBean extends BaseZnzBean {

    /**
     * addTime : 1513658827
     * addUserId : 1
     * contentId : 1
     * updateTime : 1513658827
     * contentTitle : 内容标题
     * recPosition : 0
     * contentState : 1
     * areaId : 0
     * cateId : 10
     * isTop : 0
     * contentBanner : [{"url":"http://ktjxds.meirituan.cn/staticfiles/ae21f2b5-0b6b-4633-88fe-367f0ef2c154.jpg"}]
     * clickNum : 0
     * contentType : 0
     * evaluateNum : 0
     */

    private String addTime;
    private String addUserId;
    private String contentId;
    private String updateTime;
    private String contentTitle;
    private String recPosition;
    private String contentState;
    private String areaId;
    private String cateId;
    private String isTop;
    private String clickNum;
    private String contentType;
    private String videoUrl;
    private String evaluateNum;
    private String isCollected;
    private List<ImageBean> contentBanner;
    private List<ImageBean> contentBody;

    public String getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(String isCollected) {
        this.isCollected = isCollected;
    }

    public List<ImageBean> getContentBody() {
        return contentBody;
    }

    public void setContentBody(List<ImageBean> contentBody) {
        this.contentBody = contentBody;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getRecPosition() {
        return recPosition;
    }

    public List<ImageBean> getContentBanner() {
        return contentBanner;
    }

    public void setContentBanner(List<ImageBean> contentBanner) {
        this.contentBanner = contentBanner;
    }

    public void setRecPosition(String recPosition) {
        this.recPosition = recPosition;
    }

    public String getContentState() {
        return contentState;
    }

    public void setContentState(String contentState) {
        this.contentState = contentState;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getClickNum() {
        return clickNum;
    }

    public void setClickNum(String clickNum) {
        this.clickNum = clickNum;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getEvaluateNum() {
        return evaluateNum;
    }

    public void setEvaluateNum(String evaluateNum) {
        this.evaluateNum = evaluateNum;
    }
}
