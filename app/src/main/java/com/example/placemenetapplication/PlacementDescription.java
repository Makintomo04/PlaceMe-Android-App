package com.example.placemenetapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class PlacementDescription extends AppCompatActivity {

    public static final String TAG = "My Activity";
    ImageView logoImageView;
    TextView nameTextView;
    TextView companyNameTextView;
    TextView locationTextView;
    TextView salaryTextView;
    TextView descriptionTextView;
    Button previousButton;
    Button nextButton;
    Button applyButton;
    DatabaseHelper db;
    Cursor cursor;
    int position;
    String source;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_description);
        getSupportActionBar().setTitle("Placement Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        this.source = intent.getStringExtra("Class");

        if (source.equals("Favorites")) {
            this.cursor = db.getFav();
        } else {
            if (intent.hasExtra("keywords")) {
                this.keyword = intent.getStringExtra("keywords");
                this.cursor = db.getData(keyword);
            } else {
                this.cursor = db.getData("");
            }
        }

        position = intent.getIntExtra("Position", 0);
        logoImageView = findViewById(R.id.imageView);
        nameTextView = findViewById(R.id.nameTextView);
        companyNameTextView = findViewById(R.id.companyNameTextView);
        locationTextView = findViewById(R.id.locationTextView);
        salaryTextView = findViewById(R.id.salaryTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        previousButton = findViewById(R.id.previousPlacementButton);
        nextButton = findViewById(R.id.nextPlacementButton);
        applyButton = findViewById(R.id.applyButton);

        setDataOnScreen();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextPlacement();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousPlacement();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyForPlacement(cursor.getString(11));
            }
        });


    }

    public void setDataOnScreen(){
        cursor.moveToPosition(position);

        String imgName = cursor.getString(2).toLowerCase();
        imgName = imgName.replace(" ", "");
        int resource = this.getResources().getIdentifier(imgName,
                "drawable", this.getPackageName());
        logoImageView.setImageResource(resource);

        nameTextView.setText(cursor.getString(1));
        companyNameTextView.setText(cursor.getString(2));
        locationTextView.setText(cursor.getString(6));
        salaryTextView.setText(cursor.getString(5));
        descriptionTextView.setText( cursor.getString(7));
        Log.d(TAG, "setDataOnScreen: assigned values");


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.source.equals("Favorites")) {
            Intent myIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
            this.startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getApplicationContext(), SearchResults.class);
            myIntent.putExtra("keywords", keyword);
            this.startActivity(myIntent);
        }
        return true;
    }


    public void showNextPlacement(){
        if(cursor.isLast() != true){
            position++;
            setDataOnScreen();
        }

    }

    public void showPreviousPlacement(){
        if(cursor.isFirst() != true){
            position--;
            setDataOnScreen();
        }
    }

    public void applyForPlacement(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


}
