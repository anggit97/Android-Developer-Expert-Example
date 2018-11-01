package com.anggitprayogo.dicoding.kamusoffline.adapter;

import android.widget.Filter;

import com.anggitprayogo.dicoding.kamusoffline.entity.Kamus;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter extends Filter{

    TransalateAdapter adapter;
    ArrayList<Kamus> kamuses;

    public SearchFilter(TransalateAdapter adapter, ArrayList<Kamus> kamuses) {
        this.adapter = adapter;
        this.kamuses = kamuses;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if (constraint != null && constraint.length() > 0){

            constraint = constraint.toString().toLowerCase();

            ArrayList<Kamus> kamusesCopy = new ArrayList<>();

            for (int i = 0; i < kamuses.size(); i++){

                if (kamuses.get(i).getKosakata().toLowerCase().contains(constraint)){
                    kamusesCopy.add(kamuses.get(i));
                }
            }

            filterResults.count = kamusesCopy.size();
            filterResults.values = kamusesCopy;
        }else{
            filterResults.count = kamuses.size();
            filterResults.values = kamuses;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.kamuses = (ArrayList<Kamus>) results.values;

        adapter.notifyDataSetChanged();
    }
}
