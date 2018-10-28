package com.anggitprayogo.dicoding.myweatherloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter{

    private ArrayList<WheaterItems> items = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public WeatherAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItems(ArrayList<WheaterItems> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(WheaterItems wheaterItems){
        items.add(wheaterItems);
        notifyDataSetChanged();
    }

    public void clearData(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public WheaterItems getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView != null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.weather_item, null);
            holder.tvNamaKota = convertView.findViewById(R.id.textKota);
            holder.tvDescription = convertView.findViewById(R.id.textDesc);
            holder.tvTemp = convertView.findViewById(R.id.textTemp);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvNamaKota.setText(items.get(position).getNama());
        holder.tvTemp.setText(items.get(position).getTemprature());
        holder.tvDescription.setText(items.get(position).getDescription());

        return convertView;
    }

    private static class ViewHolder{
        TextView tvNamaKota, tvTemp, tvDescription;
    }
}
