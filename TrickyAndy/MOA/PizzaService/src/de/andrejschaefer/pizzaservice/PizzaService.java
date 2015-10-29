package de.andrejschaefer.pizzaservice;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PizzaService extends ListActivity implements ActionMode.Callback {

    private int selectedItem;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        registerForContextMenu(getListView());
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedItem = i;
                PizzaService.this.startActionMode(PizzaService.this);
                view.setSelected(true);
                return true;
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);

        Intent i;

        switch(position)
        {
            default:
                makeToastLogText(l.getItemAtPosition(position).toString());
        }
    }

    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Context Menu");

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.context_menu, menu);
    }

*/
    /*
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        makeToastLogText("[ " + String.valueOf(item.getItemId()) + " | " + item.getTitle() + "]");

        return true;
    }
    */

    public void makeToastLogText(String text) {

        Toast.makeText(this, "Button " + text + " has been touched!", Toast.LENGTH_SHORT).show();
        Log.i("INFOANDREJ", "Buttonclick: " + text);
    }

    //<editor-fold desc="ActionMode Methods">
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

        MenuInflater mi = getMenuInflater();

        mi.inflate(R.menu.context_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

        makeToastLogText("[ " + String.valueOf(getListView().getChildAt(selectedItem).getId()) + " | " + getListView().getItemAtPosition(selectedItem) + "]");

        actionMode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

        actionMode = null;
    }

    //</editor-fold>

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("INFOANDREJ", "salami finished");
        this.finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        Log.i("INFOANDREJ", "pizza stop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i("INFOANDREJ", "pizza pause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("INFOANDREJ", "pizza resume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i("INFOANDREJ", "pizza restart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i("INFOANDREJ", "pizza destroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i("INFOANDREJ", "pizza start");
        super.onStart();
    }
}
