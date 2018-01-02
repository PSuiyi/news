package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.CateBean;
import com.znz.news.ui.home.ResultAct;

import java.util.List;

import butterknife.Bind;

public class TypeHorizontalAdapter extends BaseQuickAdapter<CateBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    public TypeHorizontalAdapter(@Nullable List dataList) {
        super(R.layout.item_gv_type, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvTitle, bean.getCateName());

        switch (helper.getLayoutPosition() % 6) {
            case 0:
                tvTitle.setBackgroundResource(R.mipmap.hong);
                break;
            case 1:
                tvTitle.setBackgroundResource(R.mipmap.cheng);
                break;
            case 2:
                tvTitle.setBackgroundResource(R.mipmap.huang);
                break;
            case 3:
                tvTitle.setBackgroundResource(R.mipmap.lv);
                break;
            case 4:
                tvTitle.setBackgroundResource(R.mipmap.lan);
                break;
            case 5:
                tvTitle.setBackgroundResource(R.mipmap.zi);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("cateId", bean.getCateId());
        bundle.putString("cateName", bean.getCateName());
        gotoActivity(ResultAct.class, bundle);
    }
}
