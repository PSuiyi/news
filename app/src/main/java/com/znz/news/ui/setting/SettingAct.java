package com.znz.news.ui.setting;

import android.os.Bundle;

import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.rowview.ZnzRowDescription;
import com.znz.compass.znzlibray.views.rowview.ZnzRowGroupView;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.ui.login.LoginAct;
import com.znz.news.utils.DataCleanManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/8.
 */

public class SettingAct extends BaseAppActivity {

    @Bind(R.id.commonRowGroup)
    ZnzRowGroupView commonRowGroup;
    private ArrayList<ZnzRowDescription> rowDescriptionList;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_setting, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("设置");
    }

    @Override
    protected void initializeView() {
        rowDescriptionList = new ArrayList<>();
        try {
            rowDescriptionList.add(new ZnzRowDescription.Builder()
                    .withTitle("清除缓存")
                    .withValue(DataCleanManager.getTotalCacheSize(activity))
                    .withEnableArraw(true)
                    .withOnClickListener(v -> {
                        new UIAlertDialog(activity)
                                .builder()
                                .setMsg("是否确定清理缓存？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", v2 -> {
                                    DataCleanManager.clearAllCache(context);
                                    rowDescriptionList.get(9).setValue("0.0MB");
                                    commonRowGroup.notifyDataChanged(rowDescriptionList);
                                })
                                .show();
                    })
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("检查更新")
                .withValue("已是最新版")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
//                    if (versionBean.getIs_update().equals("1")) {
//                        mDataManager.showToast("已经是最新版");
//                    } else {
//                        new UIAlertDialog(activity)
//                                .builder()
//                                .setMsg("检测到有最新版本，是否更新")
//                                .setNegativeButton("取消", null)
//                                .setPositiveButton("确定", v2 -> {
//                                    mDataManager.openUrl(versionBean.getUrl());
//                                })
//                                .show();
//                    }
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("修改密码")
                .withEnableArraw(true)
                .withOnClickListener(v -> {

                })
                .build());
        commonRowGroup.notifyDataChanged(rowDescriptionList);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvLogOut)
    public void onViewClicked() {
        new UIAlertDialog(activity)
                .builder()
                .setMsg("是否确定注销账号")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v2 -> {
                    mDataManager.logout(activity, LoginAct.class);
                })
                .show();
    }
}
