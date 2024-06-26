package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

/**
 * This class is the page where all the ride offers and request
 * from the user that is logged in is shown.
 */
public class ProfileActivity extends AppCompatActivity implements EditRideDialogFragment.EditRideDialogListener {

    private String DEBUG_TAG = "Profile Activty";

    private LinearLayout parentLayout;
    private TextView creditView;

    private RecyclerView recyclerView;
    private RideRecyclerAdapter recyclerAdapter;

    private int userStatus;

    private List<Ride> ridesList;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        recyclerView = findViewById( R.id.recyclerView );

        ridesList = new ArrayList<Ride>();

        Intent intent = getIntent();
        userStatus = intent.getIntExtra("user status", 1);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RideRecyclerAdapter( userStatus, ridesList, ProfileActivity.this );
        recyclerView.setAdapter( recyclerAdapter );


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        Log.d("User Id", userId);

        database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("rides");
        Query riderQuery = myRef.orderByChild("riderId").equalTo(userId);

        Query driverQuery = myRef.orderByChild("driverId").equalTo(userId);


        riderQuery.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
//                ridesList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );
                    ridesList.add( ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

        driverQuery.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
//                ridesList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );
                    ridesList.add( ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );
    }

    public void updateRide(int position, Ride ride, int action) {
        if (action == EditRideDialogFragment.SAVE) {
            Log.d(DEBUG_TAG, "Updating ride at: " + position + "(" + ride.getKey() + ")");

            recyclerAdapter.notifyItemChanged(position);

//            DatabaseReference ref = database
//                    .getReference()
//                    .child("rides")
//                    .child(ride.getKey());

            DatabaseReference ref = database.getReference("rides").child(ride.getKey());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DEBUG_TAG, "updated ride at: " + position + "(" + ride.getKey() + ")");
                            Toast.makeText(getApplicationContext(), "Ride updated for " + ride.getRider(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(DEBUG_TAG, "failed to update ride at: " + position + "(" + ride.getKey() + ")");
                    Toast.makeText(getApplicationContext(), "Failed to update " + ride.getKey(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else if (action == EditRideDialogFragment.DELETE) {
            Log.d(DEBUG_TAG, "Deleting ride at: " + position + "(" + ride.getKey() + ")");

            ridesList.remove(position);

            recyclerAdapter.notifyItemRemoved(position);

//            DatabaseReference ref = database
//                    .getReference()
//                    .child("rides")
//                    .child(ride.getKey());

            DatabaseReference ref = database.getReference("rides").child(ride.getKey());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DEBUG_TAG, "deleted ride at: " + position + "(" + ride.getKey() + ")");
                            Toast.makeText(getApplicationContext(), "Ride deleted for " + ride.getRider(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(DEBUG_TAG, "failed to delete ride at: " + position + "(" + ride.getKey() + ")");
                    Toast.makeText(getApplicationContext(), "Failed to delete " + ride.getKey(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}