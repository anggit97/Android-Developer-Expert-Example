package com.anggitprayogo.dicoding.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.anggitprayogo.dicoding.cataloguemovie.service.StackWidgetService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import static com.anggitprayogo.dicoding.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewFactory implements StackWidgetService.RemoteViewsFactory{

    private Cursor cursor;
    private Context context;
    private int widgetAppid;

    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        this.widgetAppid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        HomeMovieResponse.Results resultItem = getItem(position);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;

        try {
            bmp = Glide.with(context)
                    .asBitmap()
                    .load(ServiceGenerator.BASE_URL_IMAGE.concat(resultItem.getPosterPath()))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        remoteViews.setTextViewText(R.id.tv_date_release,resultItem.getReleaseDate());
        remoteViews.setImageViewBitmap(R.id.imageView, bmp);

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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private HomeMovieResponse.Results getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new HomeMovieResponse.Results(cursor);
    }
}