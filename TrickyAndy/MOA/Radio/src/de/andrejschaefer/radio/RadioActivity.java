package de.andrejschaefer.radio;


import android.app.ListActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.ActionBar;

public class RadioActivity extends ListActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer _mp;
    private String _source;
    private String url;
    private MyRadioService mrs;

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
    //</editor-fold>

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.set_source("");
        this.url ="";
        this._mp = new MediaPlayer();
        this._mp.setOnPreparedListener(this);

        registerForContextMenu(getListView());

        mrs = new MyRadioService();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.options_menu, menu);

        //ActionBar ab = getActionBar();
        //ab.show();

        return true; //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.om_exit:
                this.finish();
                System.exit(0);
                break;
            case R.id.om_options:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        if (this.get_mp() == null) {
            this.set_mp(new MediaPlayer());
        }

        this.setSourceByPosition(position);

        if (position < 3)
            this.startMediaPlayer();
    }

    public void setSourceByPosition(int position) {
        switch (position) {
            case 0:
                this.set_source("http://1live.akacast.akamaistream.net/7/706/119434/v1/gnl.akacast.akamaistream.net/1live");
                this.url = "http://www.einslive.de/einslive/index.html";
                Log.d("ANDRJE", "1Live clicked");
                break;

            case 1:
                this.set_source("http://stream.dradio.de/7/249/142684/v1/gnl.akacast.akamaistream.net/dradio_mp3_dlf_m");
                this.url = "http://www.deutschlandradio.de/";
                Log.d("ANDRJE", "Dradio clicked");
                break;

            case 2:
                this.set_source("http://panel2.directhostingcenter.com:8494/;?1445364480990.mp3");
                this.url = "http://www.radiowax.com/";
                Log.d("ANDRJE", "Radiowax clicked");
                break;

            case 3:
                stopMediaPlayer();
                Log.d("ANDRJE", "Stop clicked");
                break;

            case 4:
                Log.d("ANDRJE", "Exit clicked");
                this.finish();
                System.exit(0);
        }
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Context menu");

        if (((AdapterView.AdapterContextMenuInfo) menuInfo).position < 3) {
            this.setSourceByPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cm_start:
                this.startMediaPlayer();
                break;
            case R.id.cm_webpage:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.url));
                startActivity(browserIntent);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.get_mp().start();
        Log.d("ANDREJ", "Player started.");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        this.stopMediaPlayer();

        super.onDestroy();
    }
}
