package com.anggitprayogo.dicoding.kamusoffline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anggitprayogo.dicoding.kamusoffline.R;
import com.anggitprayogo.dicoding.kamusoffline.entity.Kamus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransalateAdapter extends RecyclerView.Adapter<TransalateAdapter.ViewHolder> {

    private ArrayList<Kamus> kamuses;
    private Context context;

    public TransalateAdapter(ArrayList<Kamus> kamuses, Context context) {
        this.kamuses = kamuses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Kamus model = kamuses.get(i);

        viewHolder.tvKosakata.setText(model.getKosakata());
    }

    @Override
    public int getItemCount() {
        if (kamuses != null) {
            return kamuses.size();
        }

        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_kosakata)
        TextView tvKosakata;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
