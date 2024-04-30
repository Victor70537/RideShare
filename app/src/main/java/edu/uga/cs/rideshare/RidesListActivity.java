package edu.uga.cs.rideshare;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RidesListActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "RidesListActivity";

    private RecyclerView recyclerView;
    private RideRecyclerAdapter recyclerAdapter;
    private List<Ride> ridesList;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);

        recyclerView = findViewById( R.id.recyclerView );

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment newFragment = new AddJobLeadDialogFragment();
//                newFragment.show( getSupportFragmentManager(), null);
            }
        });

        ridesList = new ArrayList<Ride>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RideRecyclerAdapter( ridesList, RidesListActivity.this );
        recyclerView.setAdapter( recyclerAdapter );

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rides");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {

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

                Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );

//                LinearLayout parentLayout = findViewById( R.id.parentLayout );
//
//                for (int i = 0; i < ridesList.size(); i++) {
//                    TextView textView = new TextView(getApplicationContext());
//                    textView.setText(ridesList.get(i).toString());
//                    parentLayout.addView(textView);
//                }
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

    }
}