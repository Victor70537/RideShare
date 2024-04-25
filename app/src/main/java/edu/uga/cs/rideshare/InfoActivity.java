package edu.uga.cs.rideshare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "RideShare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "InfoActivity.onCreate()");

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "InfoActivity.onCreate(): in landscape mode; finishing");
            finish();
            return;
        }

        Log.d(TAG, "InfoActivity.onCreate(): in portrait mode; replacing fragments");

        InfoFragment infoFragment = new InfoFragment();
        Log.d(TAG, "InfoActivity.onCreate(): infoFragment: " + infoFragment);

        infoFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, infoFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}