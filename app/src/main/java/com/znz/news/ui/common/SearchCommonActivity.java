package com.znz.news.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.news.R;
import com.znz.news.base.BaseAppActivity;
import com.znz.news.bean.SearchHistoryBean;
import com.znz.news.common.Constants;
import com.znz.news.db.DbManagerSearch;
import com.znz.news.event.EventRefresh;
import com.znz.news.event.EventTags;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Date： 2017/5/23 2017
 * User： PSuiyi
 * Description：
 */

public class SearchCommonActivity extends BaseAppActivity implements TextWatcher {

    private SearchHistoryFragment fragment;
    private SearchResultListFragment fragment2;

    private FragmentUtil fragmentUtil;
    private FragmentManager fragmentManager;

    private SearchHistoryBean bean;
    private DbManagerSearch dbManager;
    private String searchContent;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_activity_with_fragment, 2};
    }

    @Override
    protected void initializeVariate() {
        fragment = new SearchHistoryFragment();

        fragmentUtil = new FragmentUtil();
        fragmentManager = getSupportFragmentManager();

        dbManager = DbManagerSearch.getInstance(activity);
    }

    @Override
    protected void initializeNavigation() {
    }

    @Override
    protected void initializeView() {
        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        fragmentUtil.mContent = fragment;
        znzToolBar.setSearchHint("搜您想要的内容");
        znzToolBar.getSerachEditText().addTextChangedListener(this);
        /**
         * 设置回车键的文案
         * 去往：IME_ACTION_GO
         * 搜索：IME_ACTION_SEARCH
         * 发送：IME_ACTION_SEND
         * 下一步：IME_ACTION_NEXT
         * 完成：IME_ACTION_DONE
         */
        znzToolBar.getSerachEditText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        znzToolBar.getSerachEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //本地保存历史记录
                    if (!StringUtil.isBlank(textView.getText().toString().trim())) {
                        bean = new SearchHistoryBean();
                        bean.setName(textView.getText().toString().trim());
                        bean.setType(mDataManager.readTempData(Constants.SearchType.SEARCHTYPE));
                        dbManager.addSingleToDB(bean);
                    }
                    //收起软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    searchContent = textView.getText().toString().trim();
                    if (fragment2 == null) {
                        fragment2 = new SearchResultListFragment().newinstance(searchContent);
                    } else {
                        fragment2.newinstance(searchContent);
                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_SEARCH_VALUE, searchContent));
                    }
                    fragmentUtil.switchContent(fragment2, R.id.container, fragmentManager);
                }
                return false;
            }
        });
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        if (event.getFlag() == EventTags.REFRESH_SEARCH_VALUE) {
            znzToolBar.getSerachEditText().setText(event.getValue());
            if (fragment2 == null) {
                fragment2 = new SearchResultListFragment().newinstance(event.getValue());
            } else {
                fragment2.newinstance(event.getValue());
            }
            fragmentUtil.switchContent(fragment2, R.id.container, fragmentManager);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (StringUtil.isBlank(s.toString().trim())) {
            fragmentUtil.switchContent(fragment, R.id.container, fragmentManager);
            EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_SEARCH_HISTORY));
        }
    }
}
