package com.zd.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter 基类
 *
 * @author zhaod
 * @date 2018/10/16
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected List<T> mDatas;
    private int mCurrentTab = 0;
    private int mPreSelectedTabIndex = -1;

    private OnTabSelectListener mOnTabSelectListener;
    private OnItemClickListner onItemClickListner = null;//RecyclerView点击事件
    private OnItemLongClickListner onItemLongClickListner = null;//RecyclerView长点击事件

    public BaseRecyclerAdapter() {
    }

    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
          LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        BaseRecyclerHolder mHolder = new BaseRecyclerHolder(view);
        mHolder = getCreateViewHolder(view, viewType);
        final BaseRecyclerHolder finalMHolder = mHolder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClickListner(v, position);
                }
                if (mOnTabSelectListener != null) {
                    mPreSelectedTabIndex = position;
                    if (mCurrentTab == mPreSelectedTabIndex) {
                        mOnTabSelectListener.onTabReselect(position);
                    } else {
                        mOnTabSelectListener.onTabSelect(position);
                    }
                    mCurrentTab = mPreSelectedTabIndex;
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onItemLongClickListner != null) {
                    onItemLongClickListner.onItemLongClickListner(v, position);
                }
                return false;
            }
        });

        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        getHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            getHolder(holder, position);
        } else {
            getHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public List<T> getDatas() {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    /**
     * 刷新数据
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(List<T> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     */
    public void addDataWithoutAnim(List<T> datas) {
        if (datas == null) {
            return;
        }
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }

    /**
     * 删除列表
     */
    public void remove(int position) {
        mDatas.remove(position);
        int internalPosition = position;
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, mDatas.size() - internalPosition);
    }

    /**
     * 需要重写的方法
     */

    protected abstract int getLayoutId(int viewType);

    public abstract BaseRecyclerHolder getCreateViewHolder(View view, int viewType);

    public abstract void getHolder(BaseRecyclerHolder holder, int position);

    public void getHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
    }

    /**
     * RecyclerView点击事件
     */
    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View view, int position);
    }

    /**
     * RecyclerView长点击事件
     */
    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListner(View view, int position);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mOnTabSelectListener = onTabSelectListener;
    }

    /**
     * item点击监听
     */
    public interface OnTabSelectListener {
        /**
         * @param position 首次选中索引
         */
        void onTabSelect(int position);

        /**
         * @param position 第二次及之后选中索引
         */
        void onTabReselect(int position);
    }
}
