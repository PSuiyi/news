package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

/**
 * Date： 2017/12/27 2017
 * User： PSuiyi
 * Description：
 */

public class CommentBean extends BaseZnzBean {

    /**
     * eid : 2
     * evaluateTime : 0
     * nickname : sad翁无无
     * contentId : 4
     * avatar : http://ktjxds.meirituan.cn/staticfiles/avatar/yuibvccrvtbuij
     * content : mopmp
     * memberId : 20
     */

    private String eid;
    private String evaluateTime;
    private String nickname;
    private String contentId;
    private String avatar;
    private String content;
    private String memberId;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
