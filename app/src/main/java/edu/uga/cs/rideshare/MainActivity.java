package edu.uga.cs.rideshare;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//    public static final String TAG = "RideShare";
//    private FirebaseAuth mAuth;

    private Button logIn;
    private Button signUp;

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn = findViewById( R.id.LogInButton);
        signUp = findViewById( R.id.SignUpButton );


        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mode = 1;
                // create the intent to start the child activity
                Intent intent = new Intent( v.getContext(), LogInPage.class );
                // send the conversion type info to the child activity
                intent.putExtra("log in / sign up", mode);
                v.getContext().startActivity( intent );
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mode = 2;
                // create the intent to start the child activity
                Intent intent = new Intent( v.getContext(), LogInPage.class );
                // send the conversion type info to the child activity
                intent.putExtra("log in / sign up", mode);
                v.getContext().startActivity( intent );
            }
        });


//        TextView textView = findViewById( R.id.textView );
//
//        mAuth = FirebaseAuth.getInstance();
//        String email = "driver@mail.com";
//        String password = "password";
//
//        mAuth.signInWithEmailAndPassword( email, password )
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d( TAG, "signInWithEmail:success" );
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        }
//                        else {
//                            // If sign in fails, display a message to the user.
//                            Log.d( TAG, "signInWithEmail:failure", task.getException() );
//                            Toast.makeText( MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference( "message" );
//
//        // Read from the database value for ”message”
//        myRef.addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot dataSnapshot ) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String message = dataSnapshot.getValue( String.class );
//
//                Log.d( TAG, "Read message: " + message );
//                textView.setText( message );
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.d( TAG, "Failed to read value.", error.toException() );
//            }
//        });



    }

}