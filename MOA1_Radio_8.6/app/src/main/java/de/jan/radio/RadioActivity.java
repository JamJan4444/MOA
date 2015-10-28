package de.jan.radio;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.media.AudioManager;

public class RadioActivity extends ListActivity {

    MediaPlayer mp;
    String source = "http://173.192.224.123:8465";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        registerForContextMenu(findViewById(android.R.id.list));

        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_radio_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_options:
                Log.d("mytag","Actionbar_options pressed");
                break;
            case R.id.action_exit:
                Log.d("mytag","Actionbar_exit pressed");
                this.finish();
                break;
        }

        return true;

        //return super.onOptionsItemSelected(item);
    }

    public void play(){
        try{
            mp.setDataSource(source);
            mp.prepare();
            mp.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void onDestroy(){
        super.onDestroy();

        //stop Mediaplayer
        mp.stop();
        mp.release();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {

        Log.d("mytag", "Your choice: " + ((TextView) v).getText()
                + "\nSelected index: " + pos);

        switch (pos) {
            case 0:
                mp.reset();

                source = getString(R.string.Rock181);
                play();
                break;
            case 1:
                mp.reset();

                source = getString(R.string.PlanetRock);
                play();
                break;
            case 2:
                mp.reset();

                source = getString(R.string.EinsLive);
                play();
                break;
            case 3:
                mp.stop();
                mp.reset();
                break;
            case 4:
                this.finish();
                break;

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle((this.getListView().getItemAtPosition(((AdapterContextMenuInfo) menuInfo).position)).toString());
        getMenuInflater().inflate(R.menu.menu_radio_context, menu);

        //((AdapterContextMenuInfo)menuInfo).position

        //menu.setHeaderTitle("Context menu");
        //menu.add(ContextMenu.NONE,4711,0,"Edit entry");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.play:
                Log.d("mytag", "ContextMenu PLAY pressed");

                mp.reset();

                String test = item.getTitle().toString();
                switch(test){
                    case "EinsLive":
                        mp.reset();
                        source = getString(R.string.Rock181);
                        play();
                        break;
                    case "PlanetRock":
                        break;
                    case "Radio181":
                        break;
                }

                play();
                break;

            case R.id.website:
                Log.d("mytag","ContextMenu WEBSITE pressed");

                break;
        }

        return true;

        //return super.onContextItemSelected(item);
    }
}
