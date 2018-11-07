package com.anggitprayogo.dicoding.mystackwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;
    private String TAG = getClass().getSimpleName();


    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        mWidgetItems.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_wars_logo));
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        remoteViews.setTextViewText(R.id.banner_text, "Test");
        Log.d(TAG, "getViewAt: "+mWidgetItems.get(0).toString());

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;

//        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
//        rv.setImageViewBitmap(R.id.imageView,mWidgetItems.get(position));
//
//        Bundle extras = new Bundle();
//        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//
//        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
//        return remoteViews;
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
