package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.bean.BaseZnzBean;
import com.znz.compass.znzlibray.utils.DipUtil;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;

import java.util.List;

import butterknife.Bind;


public class CoverFlowAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    @Bind(R.id.ivImage)
    HttpImageView ivImage;

    public CoverFlowAdapter(@Nullable List<BaseZnzBean> dataList) {
        super(R.layout.item_lv_cover, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDataManager.getDeviceWidth(mContext) - DipUtil.dip2px(20), ViewGroup.LayoutParams.WRAP_CONTENT);
        ivImage.setLayoutParams(layoutParams);
        ivImage.loadRectImage("https://upload-images.jianshu.io/upload_images/5787221-e5190d98fe4ecfdd.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
