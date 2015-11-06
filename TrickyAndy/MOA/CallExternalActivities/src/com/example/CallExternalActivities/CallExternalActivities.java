package com.example.CallExternalActivities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CallExternalActivities extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(this, WebViewActivity.class);

        switch (position) {
            case 0:
                i.putExtra("uri", "http://www.google.de/");
//                i.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
//                i.setAction("android.intent.action.VIEW");
//                i.addCategory("android.intent.category.BROWSABLE");
                startActivity(i);
                break;
            case 1:
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY, "Campus Bocholt");
                startActivity(i);
                break;
            case 2:
                startActivity(new Intent(Intent.ACTION_DIAL));
                break;
            case 3:
                i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("100"));
                startActivity(i);
                break;
            case 4:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo: 51.8398,6.6527"));
                startActivity(i);
                break;
            case 5:
                i = new Intent("de.andrejschaefer.radio.intent.action.doit");
                //i.addCategory("android.intent.category.LAUNCHER");
                startActivity(i);
                break;
            case 6:
                finish();
                break;
            case 7:
                i = new Intent(Intent.ACTION_MAIN);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_HOME);
                break;
            default:
        }
    }

    @Override
    protected void onStop() {
        Log.i("INFOANDREJ", "Activity stop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i("INFOANDREJ", "Activity pause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("INFOANDREJ", "Activity resume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i("INFOANDREJ", "Activity restart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i("INFOANDREJ", "Activity destroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i("INFOANDREJ", "Activity start");
        super.onStart();
    }
}
