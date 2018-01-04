package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.nine.NineGridView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.ImageBean;
import com.znz.news.bean.NewsBean;
import com.znz.news.ui.picture.PictureDetailAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PictureAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.nineGrid)
    NineGridView nineGrid;

    public PictureAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_picture, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvTitle, bean.getContentTitle());

        if (!bean.getContentBanner().isEmpty()) {
            List<String> urls = new ArrayList<>();
            if (bean.getContentBanner().size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    urls.add(bean.getContentBanner().get(i).getUrl());
                }
            } else {
                for (ImageBean imageBean : bean.getContentBanner()) {
                    urls.add(imageBean.getUrl());
                }
            }
            nineGrid.setList(urls);
            mDataManager.setViewVisibility(nineGrid, true);
        } else {
            mDataManager.setViewVisibility(nineGrid, false);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getContentId());
        gotoActivity(PictureDetailAct.class, bundle);
    }
}
