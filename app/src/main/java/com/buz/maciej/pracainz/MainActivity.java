package com.buz.maciej.pracainz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void launch(View view) {
              //otwiera główną aktywność
        Intent intent = new Intent(this, SystemActivity.class);
        boolean failure = false;

        EditText loginText = (EditText)findViewById(R.id.login_text);
        EditText passwordText =  (EditText)findViewById(R.id.password_text);

        switch (loginText.getText().toString())
        {
            case "Pracownik":
            {
                if (passwordText.getText().toString().equals("praca"))
                {
                    intent.putExtra("layout", R.layout.activity_system);
                }
                else
                {
                    failure=true;
                }
                break;
            }

            case "Serwis":
            {
                if (passwordText.getText().toString().equals("serwis"))
                {
                    intent.putExtra("layout", R.layout.activity_system_maintenance);
                }
                else
                {
                    failure=true;
                }
                break;
            }

            case "demo":
            {
                if (passwordText.getText().toString().equals("admin"))
                {
                    intent.putExtra("layout", R.layout.activity_system_admin);
                }
                else
                {
                    failure=true;
                }
                break;
            }
            default:
            {
                failure=true;
            }
        }

        if (failure)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Niepowodzenie")
                    .setMessage("Nieprawidłowa kombinacja login/hasło")
                    .setPositiveButton("Powrót", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
            else startActivity(intent);
        }

}
