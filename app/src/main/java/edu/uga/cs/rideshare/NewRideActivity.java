package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewRideActivity extends AppCompatActivity {

    private Button UploadRideButton;

    private EditText usernameView;
    private EditText phoneView;
    private EditText destinationView;
    private EditText commentsView;

    private int userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rides);

        UploadRideButton = findViewById( R.id.UploadRideButton );
        usernameView = findViewById( R.id.editTextText );
        phoneView = findViewById( R.id.editTextPhone );
        destinationView = findViewById( R.id.editTextTextPostalAddress );
        commentsView = findViewById( R.id.editTextText1 );

        UploadRideButton.setOnClickListener( new ButtonClickListener()) ;

        Intent intent = getIntent();
        userStatus = intent.getIntExtra("user status", 1);

    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = usernameView.getText().toString();
            String phone = phoneView.getText().toString();
            String destination = destinationView.getText().toString();
            String comments = commentsView.getText().toString();
            final Ride ride = new Ride();

            if (userStatus == 1) {
                ride.setRider(username);
            } else if (userStatus == 2) {
                ride.setDriver(username);
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();

            ride.setUserId(userId);
            ride.setPhone(phone);
            ride.setDestination(destination);
            ride.setComments(comments);

            // Add a new element (Ride) to the list of rides in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("rides");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.
            myRef.push().setValue( ride )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Ride created for " + ride.getRider(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            usernameView.setText("");
                            phoneView.setText("");
                            destinationView.setText("");
                            commentsView.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed to create a Ride for " + ride.getRider(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}