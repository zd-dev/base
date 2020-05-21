package com.zd.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *ListHolder 基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public class BaseListHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public BaseListHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);

        mConvertView.setTag(this);
    }

    public static BaseListHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseListHolder(context, parent, layoutId, position);
        } else {
            BaseListHolder holder = (BaseListHolder) convertView.getTag();
            holder.mPosition = position; //即使ViewHolder是复用的，但是position记得更新一下
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
