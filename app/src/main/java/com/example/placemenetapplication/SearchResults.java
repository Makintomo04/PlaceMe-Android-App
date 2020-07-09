package com.example.placemenetapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class SearchResults extends AppCompatActivity {

    private DatabaseHelper db;
    private static ListView listView;
    private Button searchAgain;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        getSupportActionBar().setTitle("Search Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.db = new DatabaseHelper(this);
        this.listView = (ListView) findViewById(R.id.listView);
        View empty = findViewById(R.id.empty);
        this.listView.setEmptyView(empty);

        searchAgain = (Button) findViewById(R.id.searchAgainButton);

        Intent intent = getIntent();
        this.keyword = "";

        if (intent.hasExtra("keywords")) {
            this.keyword = intent.getStringExtra("keywords");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDescription(i);
            }
        });

        populateList(keyword);
    }

    private void showDescription(int i){
        Intent intent = new Intent(this, PlacementDescription.class);
        intent.putExtra("Class", "Search");
        intent.putExtra("Position", i);
        intent.putExtra("keywords", this.keyword);
        startActivity(intent);
    }

    private void populateList(String keyword) {
        Cursor cursor = db.getData(keyword);
        CustomCursorAdapter custom = new CustomCursorAdapter(this, cursor, 0);
        this.listView.setAdapter(custom);

        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResults.this, Search.class));
            }
        });
    }
}

