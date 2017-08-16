package edu.gatech.thundercats.watermap.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.thundercats.watermap.R;

public class CreatePurity extends AppCompatActivity {

    private Button returnButton;
    private Button submitButton;
    private Spinner spinnerCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_purity);
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePurity.this, MapsActivity.class));
            }
        });
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle purity report submission
                startActivity(new Intent(CreatePurity.this, MapsActivity.class));
            }
        });

        List<String> spinnerArrayCondition =  new ArrayList<String>();
        spinnerArrayCondition.add("Safe");
        spinnerArrayCondition.add("Treatable");
        spinnerArrayCondition.add("Unsafe");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayCondition);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition = (Spinner) findViewById(R.id.spinnerCondition);
        spinnerCondition.setAdapter(adapter);
    }
}
