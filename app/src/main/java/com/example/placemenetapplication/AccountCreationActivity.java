package com.example.placemenetapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountCreationActivity extends AppCompatActivity {
    DatabaseHelper db;
    String username;
    TextView backToLogin2;
    EditText usernameInput;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        backToLogin2 = (TextView) findViewById(R.id.createAccount);
        backToLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin2();
            }
        });
        db = new DatabaseHelper(this);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
//                Boolean isInserted = db.addUser(username);
                //Boolean check = db.checkUser(username);

                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Field Is Empty!", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkUsername = db.checkUsername(username);
                        if (checkUsername==true) {
                            boolean insert = db.addUser(username);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(), "You have Registered!", Toast.LENGTH_SHORT).show();
                                Intent moveToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(moveToLogin);
                            }
                        }
                            else{

                                Toast.makeText(getApplicationContext(), "Username taken, try again.", Toast.LENGTH_SHORT).show();
                            }



                }
                }


    });

}

    public void backToLogin2(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
