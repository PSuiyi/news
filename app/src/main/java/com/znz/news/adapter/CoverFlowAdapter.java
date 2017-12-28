package com.znz.news.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.utils.DipUtil;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.NewsBean;
import com.znz.news.ui.picture.PictureDetailAct;

import java.util.List;

import butterknife.Bind;


public class CoverFlowAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    @Bind(R.id.ivImage)
    HttpImageView ivImage;

    public CoverFlowAdapter(@Nullable List<NewsBean> dataList) {
        super(R.layout.item_lv_cover, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean bean) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDataManager.getDeviceWidth(mContext) - DipUtil.dip2px(50), ViewGroup.LayoutParams.WRAP_CONTENT);
        ivImage.setLayoutParams(layoutParams);
        if (!bean.getContentBanner().isEmpty()) {
            ivImage.loadRectImage(bean.getContentBanner().get(0).getUrl());
        } else {
            ivImage.setImageResource(R.mipmap.default_image_rect);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(PictureDetailAct.class);
    }
}
