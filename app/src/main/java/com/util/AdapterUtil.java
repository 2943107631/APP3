package com.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterUtil<T> extends RecyclerView.Adapter {
    private final int layoutId;
    private List<T> list = new ArrayList<>();
    private OnBindViewHolderListener<T> onBindViewHolderListener;

    public void listUpdate(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnBindViewHolderListener<T> {
        void onBindViewHolder(T data, int position, ViewHolder holder);
    }

    public AdapterUtil(int layoutId, OnBindViewHolderListener<T> onBindViewHolderListener) {
        this.layoutId = layoutId;
        this.onBindViewHolderListener = onBindViewHolderListener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        T dataItem = list.get(position);
        onBindViewHolderListener.onBindViewHolder(dataItem, position, (ViewHolder) holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        public <T extends View> T getView(int id) {
            T view = itemView.findViewById(id);
            return view;
        }
    }

}