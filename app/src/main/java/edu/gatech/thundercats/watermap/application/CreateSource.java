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

public class CreateSource extends AppCompatActivity {

    private Button returnButton;
    private Spinner spinnerType;
    private Spinner spinnerCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_source);
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateSource.this, MapsActivity.class));
            }
        });

        List<String> spinnerArrayType =  new ArrayList<String>();
        spinnerArrayType.add("Bottled");
        spinnerArrayType.add("Well");
        spinnerArrayType.add("Stream");
        spinnerArrayType.add("Lake");
        spinnerArrayType.add("Spring");
        spinnerArrayType.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayType);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerType.setAdapter(adapter);

        List<String> spinnerArrayCondition =  new ArrayList<String>();
        spinnerArrayCondition.add("Waste");
        spinnerArrayCondition.add("Treatable, Clear");
        spinnerArrayCondition.add("Treatable, Muddy");
        spinnerArrayCondition.add("Potable");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayCondition);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition = (Spinner) findViewById(R.id.spinnerCondition);
        spinnerCondition.setAdapter(adapter2);




    }
}
