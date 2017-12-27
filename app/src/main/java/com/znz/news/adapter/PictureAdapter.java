package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.nine.NineGridView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
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

        List<String> urls = new ArrayList<>();
        urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
        urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
        urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
        nineGrid.setList(urls);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getContentId());
        gotoActivity(PictureDetailAct.class, bundle);
    }
}
