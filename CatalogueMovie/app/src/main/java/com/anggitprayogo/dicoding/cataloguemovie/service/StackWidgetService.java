package com.anggitprayogo.dicoding.cataloguemovie.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.anggitprayogo.dicoding.cataloguemovie.widget.StackRemoteViewFactory;

public class StackWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewFactory(getApplicationContext(), intent);
    }
}
