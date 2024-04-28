package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;

//    private String userID;

    private int userStatus;


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

        radioGroup = findViewById( R.id.RadioGroup );
        radioButton1 = findViewById( R.id.radioButton );
        radioButton2 = findViewById( R.id.radioButton2 );

        if (mode == 1) {
            button.setText("Log In");
        } else if (mode == 2) {
            button.setText("Sign Up");
        }

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton selectedRadioButton = (RadioButton) findViewById(checkedId);
//                String selectedValue = selectedRadioButton.getText().toString();
//                // Handle the selected value
//
//                if (selectedValue.equals("Rider")) {
//                    userStatus = 1; // user is logged in as a rider
//                } else if (selectedValue.equals("Driver")){
//                    userStatus = 2; // user is logged in as a driver
//                } else {
//                    Toast.makeText(getApplicationContext(), "Select to log in as rider or driver",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
                    String selectedValue = selectedRadioButton.getText().toString();
                    // Handle the selected value
                    if (selectedValue.equals("Rider")) {
                        userStatus = 1; // user is logged in as a rider
                    } else if (selectedValue.equals("Driver")){
                        userStatus = 2; // user is logged in as a driver
                    }
                } else {
                    // No radio button is selected
                    Toast.makeText(getApplicationContext(), "Select to log in as rider or driver",
                            Toast.LENGTH_SHORT).show();
                }

                if (selectedId != -1) {

                    if (mode == 1) {

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
//                                            userID = mAuth.getCurrentUser().getUid();
                                            Log.d("Log In page", "signInWithEmail: success");

                                            // create the intent to start the child activity
                                            Intent intent = new Intent(v.getContext(), RideShareMain.class);
//                                            intent.putExtra("user", userID);
                                            intent.putExtra("user mode", userStatus);
                                            v.getContext().startActivity(intent);

                                        } else {
                                            // Sign up failed, handle failure scenario (e.g., display error message)
                                            Log.d("Log In Page", "signInWithEmail:failure", task.getException());
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
//                                            userID = mAuth.getCurrentUser().getUid();
                                            Log.d("Log In Page", "createUserWithEmailAndPassword:success");

                                            // create the intent to start the child activity
                                            Intent intent = new Intent(v.getContext(), RideShareMain.class);
//                                            intent.putExtra("user", userID);
                                            intent.putExtra("user mode", userStatus);
                                            v.getContext().startActivity(intent);

                                            // You can optionally save additional user data to the Firebase Realtime Database here, but user creation happens through Firebase Authentication
                                        } else {
                                            // Sign up failed, handle failure scenario (e.g., display error message)
                                            Log.d("Log In Page", "createUserWithEmailAndPassword:failure", task.getException());
                                        }
                                    }
                                });

                    }
                }


            }
        });


    }
}