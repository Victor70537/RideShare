package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RideShareMain extends AppCompatActivity {

    private TextView Title;
    private TextView Username;

    private Button RequestButton;
    private Button ListButton;
    private Button ProfileButton;
    private Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share_main);

        RequestButton = findViewById( R.id.RequestButton );
        ListButton = findViewById( R.id.ListButton );
        ProfileButton = findViewById( R.id.ProfileButton );
        LogoutButton = findViewById( R.id.LogoutButton );

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