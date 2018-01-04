package com.znz.news.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.utils.DipUtil;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.ui.picture.PictureDetailAct;

import java.util.List;

import butterknife.Bind;


public class CoverFlowAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivImage)
    HttpImageView ivImage;

    private String id;

    public CoverFlowAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_cover, dataList);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, String bean) {
        setOnItemClickListener(this);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(mDataManager.getDeviceWidth(mContext) - DipUtil.dip2px(30),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        ivImage.setLayoutParams(layoutParams);
        ivImage.loadRectImage(bean);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!StringUtil.isBlank(id)) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            gotoActivity(PictureDetailAct.class, bundle);
        }
    }
}
