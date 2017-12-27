package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

/**
 * Date： 2017/12/27 2017
 * User： PSuiyi
 * Description：
 */

public class UserBean extends BaseZnzBean {

    /**
     * registerTime : 1479324101
     * profile :
     * mobile : 15601585586
     * avatar :
     * token : 17b8f1ad7c1651594937a419cce98ec9
     * lastLoginIp : 127.0.0.1
     * lastLoginTime : 1513566689
     * nickname :
     * remarks :
     */

    private String registerTime;
    private String profile;
    private String mobile;
    private String avatar;
    private String token;
    private String lastLoginIp;
    private String lastLoginTime;
    private String nickname;
    private String remarks;

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
