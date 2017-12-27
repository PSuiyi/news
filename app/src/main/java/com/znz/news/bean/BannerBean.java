package com.znz.news.bean;

import com.znz.compass.znzlibray.bean.BaseZnzBean;

/**
 * Date： 2017/12/27 2017
 * User： PSuiyi
 * Description：
 */

public class BannerBean extends BaseZnzBean {

    /**
     * adBanner : http://127.0.0.1/advertisement/717e010d-5102-42c0-a71e-364a91cebd30.jpeg
     * adId : 4
     * adLink : 阿什顿飞
     * adName : 阿什顿飞
     * adOrder : 3
     * adState : 0
     */

    private String adBanner;
    private String adId;
    private String adLink;
    private String adName;
    private String adOrder;
    private String adState;

    public String getAdBanner() {
        return adBanner;
    }

    public void setAdBanner(String adBanner) {
        this.adBanner = adBanner;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdOrder() {
        return adOrder;
    }

    public void setAdOrder(String adOrder) {
        this.adOrder = adOrder;
    }

    public String getAdState() {
        return adState;
    }

    public void setAdState(String adState) {
        this.adState = adState;
    }
}
