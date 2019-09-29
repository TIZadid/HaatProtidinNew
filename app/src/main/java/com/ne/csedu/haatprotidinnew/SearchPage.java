package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        EditText edittextsearch = findViewById(R.id.etSearchtext);
        RadioButton Filterbutton =  findViewById(R.id.Filter);
        final EditText editlocation = findViewById(R.id.etlocation);
        final  EditText edittype = findViewById(R.id.ettype);
        final EditText editcolor = findViewById(R.id.etcolor);


        editlocation.setVisibility(View.GONE);
        edittype.setVisibility(View.GONE);
        editcolor.setVisibility(View.GONE);

        Filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) view;
                if (radioButton.isChecked()) {
                        editlocation.setVisibility(View.VISIBLE);
                        edittype.setVisibility(View.VISIBLE);
                        editcolor.setVisibility(View.VISIBLE);
                }
                else{
                    editlocation.setVisibility(View.GONE);
                    edittype.setVisibility(View.GONE);
                    editcolor.setVisibility(View.GONE);
                }
            }
        });
    }
}
