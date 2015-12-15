package com.buz.maciej.pracainz;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


public class SystemActivity extends FragmentActivity
implements RequestDialog.RequestDialogListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_system);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_system, menu);
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
    public void onDialogPositiveClick(DialogFragment dialog)
    {

        EditText requestIdEditText = (EditText) dialog.getDialog().findViewById(R.id.request_id_text);
        EditText exitIdEditText = (EditText) dialog.getDialog().findViewById(R.id.exit_id_text);
        Integer requestId = Integer.valueOf(requestIdEditText.getText().toString());
        Integer exitId = Integer.valueOf(exitIdEditText.getText().toString());
        WizView wizView = (WizView)findViewById(R.id.WizView);
        wizView.thread.addRequest(requestId,exitId);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {

    }

    public void mSlow(View view)
    {
        WizView wizView = (WizView)findViewById(R.id.WizView);
        wizView.thread.time=10;
    }

    public void mMedium(View view)
    {
        WizView wizView = (WizView)findViewById(R.id.WizView);
        wizView.thread.time=3;
    }

    public void mFast(View view)
    {
        WizView wizView = (WizView)findViewById(R.id.WizView);
        wizView.thread.time=1;
    }

    public void mReset(View view)
    {
        WizView wizView = (WizView)findViewById(R.id.WizView);
        wizView.thread.reset();
    }

    public void mAdd(View view)
    {
        FragmentManager fm = getFragmentManager();
        RequestDialog dialogFragment = new RequestDialog ();
        dialogFragment.show(fm, "dialog");
    }
}
