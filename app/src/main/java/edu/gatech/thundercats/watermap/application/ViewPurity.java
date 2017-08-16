package edu.gatech.thundercats.watermap.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import edu.gatech.thundercats.watermap.R;
import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.services.RemoteReportService;
import edu.gatech.thundercats.watermap.services.RemoteUserService;
import edu.gatech.thundercats.watermap.util.Callback;

public class ViewPurity extends AppCompatActivity {


    private RemoteReportService reportService;

    ListView purityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purity);
        purityView = (ListView) findViewById(R.id.purityView);
        reportService = RemoteReportService.getInstance();
        reportService.getPurityReports(new Callback<String>() {
            @Override
            public void call(final String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }, new Callback<PurityReport[]>() {
            @Override
            public void call(PurityReport[] result) {
                for (PurityReport r : result) {
                    System.out.printf("%s\n", r.toString());
                }
                ArrayAdapter<PurityReport> arrayAdapter =
                        new ArrayAdapter<PurityReport>(ViewPurity.this,
                                android.R.layout.simple_list_item_1, result);
                purityView.setAdapter(arrayAdapter);
            }
        }).execute();
    }
}
