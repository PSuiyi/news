package com.znz.news.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.eventbus.BaseEvent;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.ui.home.HomeFrag;
import com.znz.news.ui.login.LoginAct;
import com.znz.news.ui.mine.MineFrag;
import com.znz.news.ui.picture.PictureFrag;
import com.znz.news.ui.video.VideoFrag;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;


public class TabHomeAct extends BaseAppActivity {
    @Bind(R.id.main_container)
    LinearLayout mainContainer;
    @Bind(R.id.radioButton1)
    RadioButton radioButton1;
    @Bind(R.id.radioButton3)
    RadioButton radioButton3;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.radioButton2)
    RadioButton radioButton2;
    @Bind(R.id.radioButton4)
    RadioButton radioButton4;
    private HomeFrag homeFrag;
    private VideoFrag videoFrag;
    private PictureFrag pictureFrag;
    private MineFrag mineFrag;
    private FragmentUtil fragmentUtil;
    private FragmentManager fragmentManager;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_tab};
    }

    @Override
    protected void initializeVariate() {
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) {

                    } else {
                        // At least one permission is denied
                    }
                });
    }

    @Override
    protected void initializeNavigation() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        fragmentUtil = new FragmentUtil();
        fragmentManager = getSupportFragmentManager();

        if (homeFrag == null) {
            homeFrag = new HomeFrag();
        }
        fragmentManager.beginTransaction().add(R.id.main_container, homeFrag).commit();
        fragmentUtil.mContent = homeFrag;
    }

    @Override
    protected void loadDataFromServer() {
    }

    @OnClick({R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                if (homeFrag == null) {
                    homeFrag = new HomeFrag();
                }
                fragmentUtil.switchContent(homeFrag, R.id.main_container, fragmentManager);
                break;
            case R.id.radioButton2:
                if (videoFrag == null) {
                    videoFrag = new VideoFrag();
                }
                fragmentUtil.switchContent(videoFrag, R.id.main_container, fragmentManager);
                break;
            case R.id.radioButton3:
                if (pictureFrag == null) {
                    pictureFrag = new PictureFrag();
                }
                fragmentUtil.switchContent(pictureFrag, R.id.main_container, fragmentManager);
                break;
            case R.id.radioButton4:
                if (mineFrag == null) {
                    mineFrag = new MineFrag();
                }
                fragmentUtil.switchContent(mineFrag, R.id.main_container, fragmentManager);
                break;
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if (homeFrag != null) {
            if (homeFrag.onBackPressed()) {
                return;
            }
        }
        if (videoFrag != null) {
            if (videoFrag.onBackPressed()) {
                return;
            }
        }
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            DataManager.getInstance(this).showToast(R.string.app_exit_again);
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
        if (event.getFlag() == 0x90000) {
            mDataManager.logout(activity, LoginAct.class);
        }
    }
}
