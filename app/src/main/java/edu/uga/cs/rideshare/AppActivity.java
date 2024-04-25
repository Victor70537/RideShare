package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class AppActivity extends AppCompatActivity {

    private static final String TAG = "AppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Log.d(TAG, "AppActivity.onCreate(): savedInstanceState: " + savedInstanceState);
    }
}