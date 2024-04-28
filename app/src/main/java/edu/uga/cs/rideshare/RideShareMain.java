package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RideShareMain extends AppCompatActivity {

    private TextView Title;
    private TextView Username;

    private Button RequestButton;
    private Button ListButton;
    private Button ProfileButton;
    private Button LogoutButton;

    private int userStatus;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share_main);

        Title = findViewById( R.id.Title );
        Username = findViewById( R.id.Username );

        RequestButton = findViewById( R.id.RequestButton );
        ListButton = findViewById( R.id.ListButton );
        ProfileButton = findViewById( R.id.ProfileButton );
        LogoutButton = findViewById( R.id.LogoutButton );

        // get the extra and set the UI appropriately
        Intent intent = getIntent();
        userStatus = intent.getIntExtra("user mode", 1);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("RideShareMain", "This is the current user:" + currentUser.getEmail());

        // set the texts:
        if (userStatus == 1) {
            Title.setText("Use the following buttons to request a ride and view your own ride requests");
            Username.setText(currentUser.getEmail());

        } else if (userStatus == 2) {
            Title.setText("Use the following buttons to accept a ride and view your own ride offers");
            Username.setText(currentUser.getEmail());
        }


        RequestButton.setOnClickListener( new RequestButtonClickListener() );
        ListButton.setOnClickListener (new ListButtonClickListener() );




    }

    private class RequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewRideActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class ListButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), RidesListActivity.class);
            view.getContext().startActivity( intent );
        }
    }


}