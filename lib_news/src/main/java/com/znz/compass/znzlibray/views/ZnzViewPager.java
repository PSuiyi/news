package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Date： 2017/6/1 2017
 * User： PSuiyi
 * Description：控制能否滑动，以及是否有动画
 */

public class ZnzViewPager extends ViewPager {
    private boolean isCanScroll = true;
    private boolean isCanScrollAnimation = true;

    public ZnzViewPager(Context context) {
        super(context);
    }

    public ZnzViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    public void setCanScrollAnimation(boolean CanScrollAnimation) {
        isCanScrollAnimation = CanScrollAnimation;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, isCanScrollAnimation);
    }
}
