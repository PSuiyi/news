package com.znz.news.adapter;

import android.os.Bundle;
import android.view.View;

import com.znz.compass.znzlibray.views.nine.NineGridView;
import com.znz.compass.znzlibray.views.recyclerview.BaseMultiItemQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.news.R;
import com.znz.news.bean.MultiBean;
import com.znz.news.common.Constants;
import com.znz.news.ui.login.LoginAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public MultiAdapter(List dataList) {
        super(dataList);
        addItemType(Constants.MultiType.Section, R.layout.item_lv_section);
        addItemType(Constants.MultiType.Article, R.layout.item_lv_article);
        addItemType(Constants.MultiType.Video, R.layout.item_lv_video);
        addItemType(Constants.MultiType.Picture, R.layout.item_lv_picture);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBean bean) {
        setOnItemClickListener(this);
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                helper.setText(R.id.tvSection, bean.getSection());
                break;
            case Constants.MultiType.Article:
                break;
            case Constants.MultiType.Video:
                break;
            case Constants.MultiType.Picture:
                NineGridView nineGrid = helper.getView(R.id.nineGrid);
                List<String> urls = new ArrayList<>();
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                urls.add("http://upload-images.jianshu.io/upload_images/3347817-717ac59046411bbe.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480");
                nineGrid.setList(urls);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                if (!mDataManager.isLogin()) {
                    gotoActivity(LoginAct.class);
                    return;
                }
                break;
            case Constants.MultiType.Article:
                if (!mDataManager.isLogin()) {
                    gotoActivity(LoginAct.class);
                    return;
                }
//                bundle.putString("id", bean.getArticleBean().getId());
//                gotoActivity(ArticleDetailAct.class, bundle);
                break;
            case Constants.MultiType.Video:
                if (!mDataManager.isLogin()) {
                    gotoActivity(LoginAct.class);
                    return;
                }
//                bundle.putString("post_id", bean.getPostBean().getId());
//                gotoActivity(PostDetailAct.class, bundle);
                break;
            case Constants.MultiType.Picture:
                if (!mDataManager.isLogin()) {
                    gotoActivity(LoginAct.class);
                    return;
                }
//                bundle.putString("id", bean.getActivityBean().getId());
//                gotoActivity(ActivityDetailAct.class, bundle);
                break;

        }
    }

}
