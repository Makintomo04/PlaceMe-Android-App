package com.example.placemenetapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    String username;
    TextView createAccount;
    EditText usernameInput;
    Button signIn;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(this);
        db = new DatabaseHelper(this);
        createAccount = (TextView) findViewById(R.id.createAccount);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        signIn = (Button) findViewById(R.id.signIn);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }

        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                Boolean checkUsername = db.checkUsername2(username);
                if(checkUsername==true) {
                    session.setUsername(username);
                    Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                startMenu();
                }
                    else
                    Toast.makeText(getApplicationContext(), "Log In Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void startMenu () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void createAccount () {
        Intent intent = new Intent(this, AccountCreationActivity.class);
        startActivity(intent);
    }


}