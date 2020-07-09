package com.example.placemenetapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText phoneText;
    EditText dobText;
    EditText addressText;
    EditText aboutText;
    EditText experienceText;
    EditText uniText;
    Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.nameText = (EditText)findViewById(R.id.editText9);
        this.emailText = (EditText)findViewById(R.id.editText2);
        this.phoneText = (EditText)findViewById(R.id.editText3);
        this.dobText = (EditText)findViewById(R.id.editText4);
        this.addressText = (EditText)findViewById(R.id.editText5);
        this.aboutText = (EditText)findViewById(R.id.editText6);
        this.experienceText = (EditText)findViewById(R.id.editText7);
        this.uniText = (EditText)findViewById(R.id.editText8);

        saveProfileButton = (Button) findViewById(R.id.profileSaveButton);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }

    public void showMenu() {
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String phoneValue = phoneText.getText().toString();
        int phone=0;
        if(!phoneValue.equals("null") && !phoneValue.equals("")) {
            phone = Integer.parseInt(phoneValue);
        }
        String dob = dobText.getText().toString();
        String address = addressText.getText().toString();
        String about = aboutText.getText().toString();
        String experience = experienceText.getText().toString();
        String university = uniText.getText().toString();

        DatabaseHelper db = new DatabaseHelper(this);
        db.addProfile(name, email, phone, dob, address, about, experience, university);

        Toast.makeText(getApplicationContext(), "Profile Saved!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
