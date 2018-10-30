package com.anggitprayogo.dicoding.mynotesapp;

import android.view.View;

public class OnItemClickListener implements View.OnClickListener{

    private int position;
    private OnItemClickCallback onItemCallback;

    public OnItemClickListener(int position, OnItemClickCallback onItemCallback) {
        this.position = position;
        this.onItemCallback = onItemCallback;
    }

    @Override
    public void onClick(View v) {
        onItemCallback.onItemClicked(v, position);
    }

    public interface OnItemClickCallback{
        void onItemClicked(View view, int position);
    }
}
