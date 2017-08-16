package edu.gatech.thundercats.watermap.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import edu.gatech.thundercats.watermap.R;

public class ViewSource extends AppCompatActivity {

    ListView sourceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_source);
        sourceView = (ListView) findViewById(R.id.sourceView);
    }
}
