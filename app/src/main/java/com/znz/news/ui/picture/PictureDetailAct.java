package com.znz.news.ui.picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.ViewPageAdapter;
import com.znz.news.base.BaseAppActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/12/18 2017
 * User： PSuiyi
 * Description：
 */

public class PictureDetailAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.flComment)
    FrameLayout flComment;
    @Bind(R.id.ivFav)
    ImageView ivFav;
    @Bind(R.id.commonViewPager)
    ViewPager commonViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_picture_detail};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {
        fragmentList.add(new PictureDetailFrag());
        fragmentList.add(new PictureDetailFrag());
        fragmentList.add(new PictureDetailFrag());
        fragmentList.add(new PictureDetailFrag());

        commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), fragmentList));
        commonViewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.flComment, R.id.ivFav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.flComment:
                gotoActivity(CommentListAct.class);
                break;
            case R.id.ivFav:
                break;
        }
    }
}
