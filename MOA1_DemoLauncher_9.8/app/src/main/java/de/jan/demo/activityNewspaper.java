package de.jan.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class activityNewspaper extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_newspaper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {

        Log.d("mytag", "Your choice: " + ((TextView) v).getText()
                + "\nSelected index: " + pos);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (pos) {
            case 0:
                intent.setData(Uri.parse("http://www.wn.de/"));
                startActivity(intent);
                break;
            case 1:
                intent.setData(Uri.parse("http://www.bild.de"));
                startActivity(intent);
                break;
            case 2:
                intent.setData(Uri.parse("http://www.sueddeutsche.de"));
                startActivity(intent);
                break;
            case 3:
                this.finish();
                break;
        }
    }
}
