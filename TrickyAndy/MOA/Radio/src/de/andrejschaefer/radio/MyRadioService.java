package de.andrejschaefer.radio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Andrej on 21.10.2015.
 */
public class MyRadioService extends Service implements MediaPlayer.OnPreparedListener {

    MediaPlayer _mp;
    private String _source;

    //<editor-fold desc="Getter | Setter">
    public MediaPlayer get_mp() {
        return _mp;
    }

    public void set_mp(MediaPlayer _mp) {
        this._mp = _mp;
    }

    public String get_source() {
        return _source;
    }

    public void set_source(String _source) {
        this._source = _source;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //</editor-fold>


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.set_mp(new MediaPlayer());
        this.set_source("");

        return super.onStartCommand(intent, flags, startId);
    }

    public void stopMediaPlayer() {
        if (this.get_mp().isPlaying()) {
            this.get_mp().pause();
            this.get_mp().reset();
        }
        Log.d("ANDRJE", "Player stopped and reseted");
    }

    public void startMediaPlayer() {
        try {
            stopMediaPlayer();
            this.get_mp().setDataSource(this.get_source());
            this.get_mp().setAudioStreamType(AudioManager.STREAM_MUSIC);
            this.get_mp().prepareAsync();
            Log.d("ANDRJE", "Player preparing...");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ANDREJ", "Player failed.");
            Log.d("ANDREJ", e.getLocalizedMessage());
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
