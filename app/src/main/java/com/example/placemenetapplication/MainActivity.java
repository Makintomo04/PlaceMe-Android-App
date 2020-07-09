package com.example.placemenetapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button favouritesButton;
    Button searchButton;
    Button preferencesButton;
    Button profileButton;
    TextView backToLogin;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DatabaseHelper(this).getWritableDatabase();

        backToLogin = (TextView) findViewById(R.id.backToLogin);
        favouritesButton = (Button) findViewById(R.id.favouritesButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        preferencesButton = (Button) findViewById(R.id.preferencesButton);
        profileButton = (Button) findViewById(R.id.profileButton);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin();
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavourites();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearch();
            }
        });

        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrefrences();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfile();
            }
        });


    }

    public void backToLogin(){
        session = new Session(this);
        session.clearSession();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void showSearch(){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void showFavourites(){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void showPrefrences(){
        Intent intent = new Intent(this, PrefrencesActivity.class);
        startActivity(intent);
    }

    public void showProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
