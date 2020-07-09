package com.example.placemenetapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PrefrencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayAdapter<CharSequence> adapter;
    String isPaid = ""; String isUnpaid = "";
    String type = ""; String typeSummer = "";
    int p = 450;
    Switch paid;
    Switch unpaid;
    Switch oneYear;
    Switch summer;
    SeekBar seek;
    Spinner mySpinner;
    Switch relocateSwitch;
    Switch no;
    String text;
    boolean relocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefrences);
        seek = (SeekBar) findViewById(R.id.seekBar);

        getSupportActionBar().setTitle("Preferences");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner Spinner = findViewById(R.id.Spinner1);
        Spinner.setPrompt("Select an item");
        adapter = ArrayAdapter.createFromResource(this, R.array.Course, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(this);

        Button addPreferences = (Button) findViewById(R.id.button2);

        final DatabaseHelper db = new DatabaseHelper(this);

        paid = (Switch) findViewById(R.id.switch3);
        unpaid = (Switch) findViewById(R.id.switch4);
        oneYear = (Switch) findViewById(R.id.switch5);
        summer = (Switch) findViewById(R.id.switch6);
        mySpinner = (Spinner) findViewById(R.id.Spinner1);
        relocateSwitch = (Switch) findViewById(R.id.switch1);
        no = (Switch) findViewById(R.id.switch2);

        seek.setMax(500);
        seek.setProgress(450);
        checkPreferences(db);

        paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isPaid = "Paid";
                } else {
                    isPaid = "";
                }
            }
        });

        unpaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isUnpaid = "Unpaid";
                } else {
                    isUnpaid = "";
                }
            }
        });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                p = seek.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        oneYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Placement Year";
                } else {
                    type = "";
                }
            }
        });

        summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    typeSummer = "Summer Internship";
                } else {
                    typeSummer = "";
                }
            }
        });

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = mySpinner.getSelectedItem().toString();

                if(text.equals("All courses")) {
                    text = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        relocateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    relocate = true;
                    no.setChecked(false);
                } else {
                    relocate = false;
                }
            }
        });

        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    relocate = false;
                    relocateSwitch.setChecked(false);
                } else {
                    relocate = true;
                }
            }
        });

        addPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((isPaid.equals("Paid")) && (isUnpaid.equals("Unpaid"))) {
                    isPaid = "Both";
                } else if (isUnpaid.equals("Unpaid")) {
                    isPaid = isUnpaid;
                }

                if ((type.equals("Placement Year")) && (typeSummer.equals("Summer Internship"))) {
                    type = "Both";
                } else if (typeSummer.equals("Summer Internship")) {
                    type = typeSummer;
                }

                db.addPreferences(isPaid, type, text, p, relocate);
                startActivity(new Intent(PrefrencesActivity.this, Search.class));
            }
        });

    }

    private void checkPreferences(DatabaseHelper db) {
        Cursor cursor = db.getPreferences();
        if (cursor.moveToFirst()) {
            do {
                String paidText = cursor.getString(2);
                if (paidText.equals("Paid")) {
                    paid.setChecked(true);
                    isPaid = "Paid";
                } else if (paidText.equals("Unpaid")) {
                    unpaid.setChecked(true);
                    isUnpaid = "Unpaid";
                } else if (paidText.equals("Both")) {
                    paid.setChecked(true);
                    unpaid.setChecked(true);
                    isPaid = "Paid";
                    isUnpaid = "Unpaid";
                }

                String typeText = cursor.getString(3);
                if (typeText.equals("Placement Year")) {
                    oneYear.setChecked(true);
                    type = "Placement Year";
                } else if (typeText.equals("Summer Internship")) {
                    summer.setChecked(true);
                    typeSummer = "Summer Internship";
                } else if (typeText.equals("Both")) {
                    oneYear.setChecked(true);
                    summer.setChecked(true);
                    type = "Placement Year";
                    typeSummer = "Summer Internship";
                }

                String subject = cursor.getString(4);
                if (!subject.equals("null")) {
                    int position = adapter.getPosition(subject);
                    mySpinner.setSelection(position);
                    text = subject;
                }

                int milesNumber = cursor.getInt(5);
                seek.setProgress(milesNumber);
                p = milesNumber;

                boolean relocateValue = cursor.getInt(6) > 0;

                if (relocateValue == true) {
                    relocateSwitch.setChecked(true);
                    relocate = true;
                } else {
                    no.setChecked(true);
                    relocate = false;
                }
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void backToSearch(){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}