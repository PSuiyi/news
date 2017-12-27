package com.znz.news.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/10/13.
 */
public class WelcomeAdapter extends PagerAdapter {
    private List<View> vp_list;

    public WelcomeAdapter(List<View> list) {
        super();
        this.vp_list = list;
    }

    @Override
    public int getCount() {
        return vp_list != null ? vp_list.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(vp_list.get(position));
        return vp_list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(vp_list.get(position));
    }
}
