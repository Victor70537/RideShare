package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInPage extends AppCompatActivity {

    private Button button;
    private EditText email;
    private EditText password;

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

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // create the intent to start the child activity
                Intent intent = new Intent( v.getContext(), AppActivity.class );
                intent.putExtra("email", email.getText().toString());
                v.getContext().startActivity( intent );

            }
        });


    }
}