package com.zero.dibreak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.android.databinding.library.baseAdapters.BR;
import com.zero.dibreak.viewholder.BindingViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Base Data Binding RecyclerView Adapter.
 *
 * @author markzhai on 16/8/25
 */

public abstract class BaseViewAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {
    protected final LayoutInflater mLayoutInflater;

    protected List<T> mCollection;
    protected Presenter mPresenter;
    protected Decorator mDecorator;

    public interface Presenter {

    }

    public interface Decorator {
        void decorator(BindingViewHolder holder, int position, int viewType);
    }

    public BaseViewAdapter(Context context) {
        mCollection = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        final Object item = mCollection.get(position);
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().setVariable(BR.presenter, getPresenter());
        holder.getBinding().executePendingBindings();
        if (mDecorator != null) {
            mDecorator.decorator(holder, position, getItemViewType(position));
        }
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void remove(int position) {
        mCollection.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mCollection.clear();
        notifyDataSetChanged();
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }
}
