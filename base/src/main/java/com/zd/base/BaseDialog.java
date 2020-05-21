package com.zd.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;

/**
 * Dialog 基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public abstract class BaseDialog extends Dialog implements DialogInterface.OnKeyListener, DialogInterface.OnDismissListener {
    public static final int SELECT_FIRST = 0x01;
    public static final int SELECT_SECOND = 0x02;
    public static final int SELECT_THIRD = 0x03;
    public static final int SELECT_FOURTH = 0x04;
    public static final int SELECT_FIFTH = 0x05;
    protected OnClickCallback onClickCallback;
    protected OnContentClickCallback onContentClickCallback;
    protected OnContentTypeClickCallback onContentTypeClickCallback;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Window window = this.getWindow();
        getWindows(window);
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (metrics.widthPixels * setDialogWith());

            lp.alpha = setWindowBackgroundAlpha();
            lp.dimAmount = setBackgroundDimAlpha();
            window.setAttributes(lp);
        }
        initView();
        initDatas();
        initEvents();
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return !setOnKeyDown();
        }
        return false;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        onDismissDialog(dialog);
    }

    /**
     * 获取布局文件
     */
    protected abstract int getContentView();

    /**
     * 获取Window
     */
    protected void getWindows(Window window) {
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initDatas() {
    }

    /**
     * 监听
     */
    protected void initEvents() {
        setCanceledOnTouchOutside(setOnBackgroundDismiss());
        setOnDismissListener(this);
        setOnKeyListener(this);
    }

    /**
     * 点击阴影区域
     *
     * @return true默认可关闭
     */
    protected boolean setOnBackgroundDismiss() {
        return true;
    }

    /**
     * 点击手机返回是否关闭
     *
     * @return false默认可关闭
     */
    protected boolean setOnKeyDown() {
        return true;
    }

    /**
     * dialog宽度
     *
     * @return 默认写1.0f
     */
    protected float setDialogWith() {
        return 1.0f;
    }

    /**
     * dialog透明度
     *
     * @return 默认写1.0f
     */
    protected float setWindowBackgroundAlpha() {
        return 1.0f;
    }

    /**
     * 外黑色背景透明度
     *
     * @return 默认写0.6f
     */
    protected float setBackgroundDimAlpha() {
        return 0.6f;
    }

    protected void onDismissDialog(DialogInterface dialog) {
    }

    /**
     * 点击回调 type类型
     */
    public interface OnClickCallback {
        void onClickType(int type);
    }

    public BaseDialog setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
        return this;
    }

    /**
     * 点击回调内容
     */
    public interface OnContentClickCallback {
        void onContentClick(String content);
    }

    public BaseDialog setOnContentClickCallback(OnContentClickCallback onContentClickCallback) {
        this.onContentClickCallback = onContentClickCallback;
        return this;
    }

    /**
     * 点击回调内容和类型
     */
    public interface OnContentTypeClickCallback {
        void onContentTypeClick(int type, String content);
    }

    public BaseDialog setOnContentTypeClickCallback(OnContentTypeClickCallback onContentTypeClickCallback) {
        this.onContentTypeClickCallback = onContentTypeClickCallback;
        return this;
    }
}












