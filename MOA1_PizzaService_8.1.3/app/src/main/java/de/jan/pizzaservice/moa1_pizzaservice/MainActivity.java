package de.jan.pizzaservice.moa1_pizzaservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onPizzaChoiceMenuClick(View button){
       switch(button.getId()){
           case R.id.margherita:
               Log.d("myTag", "Pizza_Margherita has been ordered");
               Toast.makeText(this,"You added PIZZA MARGHERITA to your order",Toast.LENGTH_LONG).show();
               break;
           case R.id.salami:
               Log.d("myTag", "Pizza_Salami has been ordered");
               Toast.makeText(this,"You added PIZZA SALAMI to your order",Toast.LENGTH_LONG).show();
               break;
           case R.id.tonno:
               Log.d("myTag", "Pizza_Tonno has been ordered");
               Toast.makeText(this,"You added PIZZA TONNO to your order",Toast.LENGTH_LONG).show();

               //Test Alert
               new AlertDialog.Builder(this).setMessage("Test Message").setPositiveButton(android.R.string.ok, null).show();
               break;
       }
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
}
