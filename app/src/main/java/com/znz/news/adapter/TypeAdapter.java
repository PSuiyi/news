package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.CateBean;
import com.znz.news.ui.home.ResultAct;

import java.util.List;

import butterknife.Bind;

public class TypeAdapter extends BaseQuickAdapter<CateBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.rvType)
    RecyclerView rvType;
    @Bind(R.id.tvType)
    TextView tvType;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.llCate)
    LinearLayout llCate;

    public TypeAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_type, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvType, bean.getCateName());
        mDataManager.setValueToView(tvTitle, bean.getCateDesc());

        rvType.setLayoutManager(new GridLayoutManager(mContext, 3));
        TypeSecondAdapter secondAdapter = new TypeSecondAdapter(bean.getSubCate());
        rvType.setAdapter(secondAdapter);

        llCate.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("cateId", bean.getCateId());
            bundle.putString("cateName", bean.getCateName());
            gotoActivity(ResultAct.class, bundle);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
