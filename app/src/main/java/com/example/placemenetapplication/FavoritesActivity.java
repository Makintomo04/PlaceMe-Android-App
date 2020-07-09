package com.example.placemenetapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setTitle("Favorites Activity");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createExampleList();
        buildRecyclerView();
    }

    public void removeItem(int position) {
        db = new DatabaseHelper(this);
        SQLiteDatabase database = db.getWritableDatabase();

        ExampleItem item = mExampleList.get(position);
        mExampleList.remove(position);
        db.removeFav(item.getID());
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }


    public void createExampleList() {
        mExampleList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this);

        Cursor myCursor = db.getFav();

        if (myCursor.moveToFirst()) {
            do {
                String title = myCursor.getString(1);
                String expiry = myCursor.getString(3);
                int id = myCursor.getInt(0);

                String imgName = myCursor.getString(2).toLowerCase();
                imgName = imgName.replace(" ", ""); // Get image for company name

                int resource = this.getResources().getIdentifier(imgName,
                        "drawable", this.getPackageName());

                mExampleList.add(new ExampleItem(resource, title, "Expires On "+expiry, id));

            } while (myCursor.moveToNext());
        }
        myCursor.close();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showDescription(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void showDescription(int i){
        Intent intent = new Intent(this, PlacementDescription.class);
        intent.putExtra("Class", "Favorites");
        intent.putExtra("Position", i);
        startActivity(intent);
    }
}