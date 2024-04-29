package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private String DEBUG_TAG = "Profile Activty";

    private LinearLayout parentLayout;
    private TextView creditView;

    private List<Ride> ridesList;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ridesList = new ArrayList<Ride>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("rides");
        Query query = myRef.orderByChild("userId").equalTo(userId);

//        DatabaseReference myRef = database.getReference("rides").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        query.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                ridesList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );
                    ridesList.add( ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

//                Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );

                parentLayout = findViewById( R.id.parentLayout );

                for (int i = 0; i < ridesList.size(); i++) {
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(ridesList.get(i).toString());
                    parentLayout.addView(textView);
                }

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );
    }
}