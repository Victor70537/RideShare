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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInPage extends AppCompatActivity {

    private Button button;
    private EditText email;
    private EditText password;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        Intent intent = getIntent();

        int mode = intent.getIntExtra("log in / sign up", 1);

        Log.d("LogInPage", "Mode: " + mode);


        button = findViewById( R.id.button2 );
        email = findViewById( R.id.userPassword);
        password = findViewById( R.id.userPassword);

        if (mode == 1) {
            button.setText("Log In");
        } else if (mode == 2) {
            button.setText("Sign Up");
        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mode == 1) {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        user = mAuth.getCurrentUser();
                                        Log.d("Log In page", "signInWithEmail: success");

                                        // create the intent to start the child activity
                                        Intent intent = new Intent( v.getContext(), RideShareMain.class );
                                        intent.putExtra("user", user);
                                        v.getContext().startActivity( intent );

                                    }
                                    else {
                                        // Sign up failed, handle failure scenario (e.g., display error message)
                                        Log.d("Log In Page", "signInWithEmail:failure", task.getException() );
                                    }
                                }
                            });


                } else if (mode == 2) {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign up successful, handle success scenario (e.g., navigate to main app)
                                        user = mAuth.getCurrentUser();
                                        Log.d("Log In Page", "createUserWithEmailAndPassword:success");

                                        // create the intent to start the child activity
                                        Intent intent = new Intent( v.getContext(), RideShareMain.class );
                                        intent.putExtra("user", user);
                                        v.getContext().startActivity( intent );

                                        // You can optionally save additional user data to the Firebase Realtime Database here, but user creation happens through Firebase Authentication
                                    } else {
                                        // Sign up failed, handle failure scenario (e.g., display error message)
                                        Log.d("Log In Page", "createUserWithEmailAndPassword:failure", task.getException() );
                                    }
                                }
                            });

                }


            }
        });


    }
}