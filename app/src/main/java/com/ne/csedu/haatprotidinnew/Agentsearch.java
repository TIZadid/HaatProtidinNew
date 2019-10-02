package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Agentsearch extends Activity {

    String[] locationOptions = {"None","dasd","dasdasda","aswq"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentsearch);
        final Spinner editlocation = findViewById(R.id.etagentlocation);
        final TextView locationLabel =findViewById(R.id.tvagentlocation);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,locationOptions);
        // Specify layout to be used when list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        editlocation.setAdapter(locationAdapter);
        editlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Agentsearch.this, locationOptions[position] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
