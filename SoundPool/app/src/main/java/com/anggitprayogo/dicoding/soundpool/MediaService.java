package com.anggitprayogo.dicoding.soundpool;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MediaService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{

    public static final String ACTION_PACKAGE = "com.anggitprayogo.dicoding.soundpool";
    public static final String ACTION_PLAY = "com.anggitprayogo.dicoding.soundpool.PLAY";
    public static final String ACTION_STOP = "com.anggitprayogo.dicoding.soundpool.STOP";
    public static final String ACTION_CREATE = "com.anggitprayogo.dicoding.soundpool.CREATE";

    private MediaPlayer mediaPlayer = null;
    private int serviceId = 777;

    public MediaService() {

    }

    public void init(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.music);


        try {
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch (action){
            case ACTION_CREATE:
                init();
                break;
            case ACTION_PLAY:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.prepareAsync();
                }
                break;
            case ACTION_STOP:
                mediaPlayer.stop();
                break;
        }
        return flags;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
    }
}
