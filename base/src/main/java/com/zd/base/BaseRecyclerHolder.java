package com.zd.base;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerViewHolder 基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
    }

    /**
     * 点击事件
     *
     * @param listener
     * @return
     */

    public BaseRecyclerHolder setOnClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return this;
    }
}
