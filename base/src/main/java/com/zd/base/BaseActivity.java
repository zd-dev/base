package com.zd.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.InflateException;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity 基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public abstract class BaseActivity extends AppCompatActivity {
    private boolean mInitFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
            }
            initDatas(savedInstanceState);
            initEvents();
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mInitFlag) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    initVisible();
                    return false;
                }
            });
            mInitFlag = true;
        } else {
            onReVisible();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 状态栏背景及文字颜色
     *
     * @param textBarColor 状态栏文字 true 白色  false 黑色
     */
    public void setStatusBar(boolean textBarColor) {
        StatusBarUtil.setTransparentStatusBar(this, false);
        if (textBarColor) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
    }

    /**
     * 状态栏背景及文字颜色
     *
     * @param textBarColor 状态栏文字 true 白色  false 黑色
     * @param statusBarColor true 主题色  false 透明
     */
    public void setStatusBar(boolean textBarColor, boolean statusBarColor, int colorTheme) {
        if (statusBarColor) {
            StatusBarUtil.setColor(this, colorTheme, 0);
        } else {
            StatusBarUtil.setTransparentStatusBar(this, false);
        }
        if (textBarColor) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
    }

    /**
     * 动态设置状态栏背景及文字颜色
     *
     * @param textBarColor 状态栏文字 true 白色  false 黑色
     * @param statusBarColor 状态栏背景颜色
     */
    public void setStatusBar(boolean textBarColor, int statusBarColor) {
        if (textBarColor) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
        StatusBarUtil.setColor(this, getResources().getColor(statusBarColor), 0);
    }

    /**
     * 动态设置状态栏的高度
     */
    public void setStatusBarHeight(TextView tvBar) {
        tvBar.setHeight(StatusBarUtil.getStatusBarHeight(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 动态设置状态栏的高度颜色
     */
    public void setStatusBarHeight(TextView tvBar, boolean isThemeColor, int colorTheme) {
        tvBar.setHeight(StatusBarUtil.getStatusBarHeight(this));
        if (isThemeColor) {
            tvBar.setBackgroundColor(colorTheme);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化监听事件
     */
    protected abstract int initView(Bundle savedInstanceState);

    /**
     * 初始化监听事件
     */
    protected void initDatas(Bundle savedInstanceState) {
    }

    /**
     * 初始化监听事件
     */
    protected void initEvents() {
    }

    /**
     * Activity初始化，Activity可见用于网路请求等耗时方法，第一次
     */
    protected void initVisible() {
    }

    /**
     * Activity第二次及以后
     */
    protected void onReVisible() {
    }
}

