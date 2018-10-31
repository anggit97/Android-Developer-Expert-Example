package com.anggitprayogo.dicoding.mypreloaddata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anggitprayogo.dicoding.mypreloaddata.R;
import com.anggitprayogo.dicoding.mypreloaddata.entity.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaHolder> {
    private ArrayList<Mahasiswa> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public MahasiswaAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa_row, parent, false);
        return new MahasiswaHolder(view);
    }
    public void addItem(ArrayList<Mahasiswa> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MahasiswaHolder holder, int position) {
        holder.textViewNim.setText(mData.get(position).getNim());
        holder.textViewNama.setText(mData.get(position).getNama());
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class MahasiswaHolder extends RecyclerView.ViewHolder {
        private TextView textViewNim;
        private TextView textViewNama;
        public MahasiswaHolder(View itemView) {
            super(itemView);
            textViewNim = (TextView) itemView.findViewById(R.id.txt_nim);
            textViewNama = (TextView) itemView.findViewById(R.id.txt_nama);
        }
    }
}