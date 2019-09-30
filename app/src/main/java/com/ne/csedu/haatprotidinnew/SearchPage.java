package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchPage extends AppCompatActivity {
    String[] locationOptions = {"None","dasd","dasdasda","aswq"};
    String[] typeOptions = {"None","dasd111","dasdasda","aswq"};
    String[] colorOptions = {"None","dasd222","dasdasda","aswq","sadasdas"};
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        EditText edittextsearch = findViewById(R.id.etSearchtext);

        RadioButton Filterbutton =  findViewById(R.id.Filter);
        final Spinner editlocation = findViewById(R.id.etlocation);
        final TextView locationLabel =findViewById(R.id.tvlocation);
        final Spinner edittype = findViewById(R.id.ettype);
        final TextView typeLabel = findViewById(R.id.tvtype);
        final Spinner editcolor = findViewById(R.id.etcolor);
        final TextView colorLabel = findViewById(R.id.tvcolor);


        editlocation.setVisibility(View.GONE);
        locationLabel.setVisibility(View.GONE);

        edittype.setVisibility(View.GONE);
        typeLabel.setVisibility(View.GONE);

        editcolor.setVisibility(View.GONE);
        colorLabel.setVisibility(View.GONE);



        count = 0;

        Filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) view;
                if (radioButton.isChecked() && (count ==0)) {
                        editlocation.setVisibility(View.VISIBLE);
                        locationLabel.setVisibility(View.VISIBLE);

                        edittype.setVisibility(View.VISIBLE);
                        typeLabel.setVisibility(View.VISIBLE);

                        editcolor.setVisibility(View.VISIBLE);
                        colorLabel.setVisibility(View.VISIBLE);

                        count=1;
                }
                else if(radioButton.isChecked() && count==1){
                    radioButton.setChecked(false);
                    editlocation.setVisibility(View.GONE);
                    locationLabel.setVisibility(View.GONE);

                    edittype.setVisibility(View.GONE);
                    typeLabel.setVisibility(View.GONE);

                    editcolor.setVisibility(View.GONE);
                    colorLabel.setVisibility(View.GONE);

                    count =0;
                }
            }
        });

        //LOCATION ER JONNO EKHAN THEKE ADAPTER BANANO SHURU
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,locationOptions);
        // Specify layout to be used when list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        editlocation.setAdapter(locationAdapter);
        editlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchPage.this, locationOptions[position] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //TYPE ER JONNO EKHAN THEKE
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeOptions);
        // Specify layout to be used when list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        edittype.setAdapter(typeAdapter);

        edittype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchPage.this, typeOptions[position] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //COLOR ER JONNO EKHAN THEKE
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colorOptions);
        // Specify layout to be used when list of choices appears
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        editcolor.setAdapter(colorAdapter);

        editcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchPage.this, colorOptions[position] + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
