package com.anggitprayogo.dicoding.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.service.StackWidgetService;

import java.util.ArrayList;
import java.util.List;

import static com.anggitprayogo.dicoding.cataloguemovie.widget.FavouriteWidget.EXTRA_ITEM;

public class StackRemoteViewFactory implements StackWidgetService.RemoteViewsFactory{

    private List<Bitmap> bitmaps = new ArrayList<>();
    private Context context;
    private int widgetAppid;

    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        this.widgetAppid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("SIZE", "getCount: "+bitmaps.size());
        return bitmaps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setTextViewText(R.id.tv_date, "Tanggal");
        remoteViews.setImageViewBitmap(R.id.imageView, bitmaps.get(position));

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
