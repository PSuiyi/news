package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.CateBean;
import com.znz.news.ui.home.ResultAct;

import java.util.List;

public class TypeSecondAdapter extends BaseQuickAdapter<CateBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public TypeSecondAdapter(@Nullable List dataList) {
        super(R.layout.item_gv_type_second, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateBean bean) {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("cateId", bean.getCateId());
        bundle.putString("cateName", bean.getCateName());
        gotoActivity(ResultAct.class, bundle);
    }
}
