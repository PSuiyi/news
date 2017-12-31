package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.CommentBean;

import java.util.List;

import butterknife.Bind;

public class CommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivUserHeader)
    HttpImageView ivUserHeader;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvContent)
    TextView tvContent;

    public CommentAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_comment, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvUserName, bean.getNickname());
        mDataManager.setValueToView(tvContent, bean.getContent());
        ivUserHeader.loadHeaderImage(bean.getAvatar());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
