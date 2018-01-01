package com.znz.news.ui.picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.news.R;
import com.znz.news.adapter.ViewPageAdapter;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.bean.ImageBean;
import com.znz.news.bean.NewsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.tvCountComment)
    TextView tvCountComment;
    @Bind(R.id.tvCountView)
    TextView tvCountView;
    @Bind(R.id.flView)
    FrameLayout flView;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String id;
    private NewsBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_picture_detail};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

        Map<String, String> params = new HashMap<>();
        params.put("contentId", id);
        mModel.requestNewsDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSON.parseObject(responseOriginal.getString("data"), NewsBean.class);

                mDataManager.setValueToView(tvTitle, bean.getContentTitle());
                mDataManager.setValueToView(tvCountView, bean.getClickNum());
                mDataManager.setValueToView(tvCountComment, bean.getEvaluateNum());

                if (!bean.getContentBody().isEmpty()) {
                    mDataManager.setValueToView(tvContent, bean.getContentBody().get(0).getDesc());
                    for (ImageBean imageBean : bean.getContentBody()) {
                        fragmentList.add(PictureDetailFrag.newInstance(imageBean));
                    }
                    commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), fragmentList));
                    commonViewPager.setOffscreenPageLimit(fragmentList.size());
                    commonViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            mDataManager.setValueToView(tvContent, bean.getContentBody().get(position).getDesc());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
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
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(CommentListAct.class, bundle);
                break;
            case R.id.ivFav:
                Map<String, String> params = new HashMap<>();
                params.put("contentId", id);
                mModel.requestFavAdd(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("收藏成功");
                        ivFav.setImageResource(R.mipmap.yishoucang);
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
        }
    }
}
