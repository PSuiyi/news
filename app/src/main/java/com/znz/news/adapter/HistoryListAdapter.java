package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.SearchHistoryBean;

import java.util.List;

import butterknife.Bind;

/**
 * Date： 2017/5/4 2017
 * User： PSuiyi
 * Description：
 */

public class HistoryListAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvSearchHistory)
    TextView tvSearchHistory;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    public HistoryListAdapter(@Nullable List<SearchHistoryBean> dataList) {
        super(R.layout.item_lv_history, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean bean) {
        mDataManager.setValueToView(tvSearchHistory, bean.getName());
        helper.addOnClickListener(R.id.ivDelete);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
