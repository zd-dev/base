package com.zd.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public abstract class BaseManagerFragment extends Fragment {
    protected Context mContext;
    private boolean isUserVisible;
    private boolean isFirstVisible;
    private boolean initFlag;
    private boolean isHiddenChanged;
    private boolean isViewNull;
    public View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initVariable() {
        isFirstVisible = true;
        isUserVisible = false;
        isViewNull = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isViewNull) {
            return;
        }
        if (isVisibleToUser) {
            isUserVisible = true;
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstVisible();
                initEvents();
            } else {
                onVisible();
            }
            return;
        }
        if (isUserVisible) {
            isUserVisible = false;
            onInVisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutId(), null);
            initView(inflater, container, savedInstanceState);
            initData(savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint()) {
            isUserVisible = true;
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstVisible();
                initEvents();
            } else {
                onVisible();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!initFlag) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    initVisible();
                    return false;
                }
            });
            initFlag = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (isHiddenChanged) {
                onInVisible();
            }
            isHiddenChanged = true;
        } else {
            onVisible();
        }
    }

    /**
     * 动态设置状态栏的高度
     */
    public void setStatusBarHeight(TextView tvBar) {
        tvBar.setHeight(StatusBarUtil.getStatusBarHeight(getActivity()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化 setLayoutId
     */
    public abstract int setLayoutId();

    /**
     * 初始化 View
     */
    public void initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    }

    /**
     * 初始化数据
     */
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 初始化监听事件
     */
    protected void initEvents() {
    }

    /**
     * Fragment第一次可见
     */
    protected void onFirstVisible() {
    }

    /**
     * Fragment可见
     */
    protected void onVisible() {
    }

    /**
     * Fragment不可见
     */
    protected void onInVisible() {
    }

    /**
     * Fragment初始化可见
     */
    protected void initVisible() {
    }
}
