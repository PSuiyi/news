package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TypeAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.rvType)
    RecyclerView rvType;

    public TypeAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_type, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);

        rvType.setLayoutManager(new GridLayoutManager(mContext, 3));
        List<BaseZnzBean> typeList = new ArrayList<>();
        TypeSecondAdapter secondAdapter = new TypeSecondAdapter(typeList);
        rvType.setAdapter(secondAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
