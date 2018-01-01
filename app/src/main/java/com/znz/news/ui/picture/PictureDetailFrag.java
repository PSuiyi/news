package com.znz.news.ui.picture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.news.R;
import com.znz.news.base.BaseAppFragment;
import com.znz.news.bean.ImageBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/12/18 2017
 * User： PSuiyi
 * Description：
 */

public class PictureDetailFrag extends BaseAppFragment {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.ivImage)
    HttpImageView ivImage;
    private ImageBean bean;

    public static PictureDetailFrag newInstance(ImageBean bean) {
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        PictureDetailFrag fragment = new PictureDetailFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_picture_detail};
    }

    @Override
    protected void initializeVariate() {
        if (getArguments() != null) {
            bean = (ImageBean) getArguments().getSerializable("bean");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {
        ivImage.loadVerImage(bean.getUrl());
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
