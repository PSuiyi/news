package com.znz.news.ui.login;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.news.R;
import com.znz.news.adapter.WelcomeAdapter;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.ui.TabHomeAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/10/27 2017
 * User： PSuiyi
 * Description：
 */

public class WelcomeAct extends BaseAppActivity {
    private ViewPager vpWelcome;
    int oldTemp;// 记录变化状态的最大值
    private int currIndex = 0;
    List<View> list = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_welcome};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        vpWelcome = ViewHolder.init(this, R.id.vpWelcome);

        list.add(LayoutInflater.from(activity).inflate(R.layout.item_welcome_viewpager1, null));
        list.add(LayoutInflater.from(activity).inflate(R.layout.item_welcome_viewpager2, null));
        View view = LayoutInflater.from(activity).inflate(R.layout.item_welcome_viewpager3, null);
        list.add(view);

        bindViewById(view, R.id.ivImage).setOnClickListener(v -> {
            if (mDataManager.isLogin()) {
                gotoActivity(TabHomeAct.class);
            } else {
                gotoActivity(LoginAct.class);
            }
            finish();
        });

        WelcomeAdapter adapter = new WelcomeAdapter(list);
        vpWelcome.setAdapter(adapter);
        vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                currIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int index) {
                // 记录状态变化的最大值
                int i = index;
                if (i >= oldTemp) {
                    oldTemp = i;
                }
                if (i == 0) {
                    if (currIndex == list.size() - 1 && oldTemp == 1) {
                        if (mDataManager.isLogin()) {
                            gotoActivity(TabHomeAct.class);
                        } else {
                            gotoActivity(LoginAct.class);
                        }
                        finish(); // 结束启动动画界面
                    }
                    oldTemp = 0;
                }
            }
        });

    }

    @Override
    protected void loadDataFromServer() {

    }
}
