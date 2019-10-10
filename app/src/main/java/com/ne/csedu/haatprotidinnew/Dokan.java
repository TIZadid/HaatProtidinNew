package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Dokan extends AppCompatActivity {


    private static final String TAG = "SearchPage";
    private  static  final int NUM_COLUMNS=2;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mPrice=new ArrayList<>();
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mID = new ArrayList<>();

    ArrayList<String> locationOptions = new ArrayList<String>() {};

    ArrayList<String> categoryOptions = new ArrayList<String>() {};

    ArrayList<String> colorOptions = new ArrayList<String>() {};

    int count;
    int index,userIndex;

    Context context;


    ArrayList<String> currentFilters = new ArrayList<>();

    private RecyclerView htRecyclerView;
    private HorTagRecyclerViewAdapter htAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokan);

        context = this;

        //hortag start
        htRecyclerView = findViewById(R.id.hor_tag_recycler_view);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        htRecyclerView.setLayoutManager(layoutManager);

        htAdapter = new HorTagRecyclerViewAdapter(currentFilters,this);
        htRecyclerView.setAdapter(htAdapter);


        EditText edittextsearch = findViewById(R.id.etSearchtext);

        Switch Filterbutton =  findViewById(R.id.Filter);
        final Spinner editlocation = findViewById(R.id.etlocation);

        final Spinner edittype = findViewById(R.id.ettype);

        final Spinner editcolor = findViewById(R.id.etcolor);

        editlocation.setVisibility(View.GONE);

        edittype.setVisibility(View.GONE);

        editcolor.setVisibility(View.GONE);

        count = 0;

        currentFilters = getIntent().getStringArrayListExtra("currentFilters");
        if(currentFilters == null) currentFilters = new ArrayList<>();
        System.out.println(currentFilters.size()+"saddasdasds\n\n\n");
        if(currentFilters.size()>0)
        {
            for(int ind=0;ind<currentFilters.size();ind++)
                System.out.println(currentFilters.get(ind));
        }
        Collections.sort(currentFilters);

        Filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch radioButton = (Switch) view;
                if (radioButton.isChecked()) {
                    editlocation.setVisibility(View.VISIBLE);

                    edittype.setVisibility(View.VISIBLE);

                    editcolor.setVisibility(View.VISIBLE);

                    count=1;
                }
                else if(!radioButton.isChecked()){
                    radioButton.setChecked(false);
                    editlocation.setVisibility(View.GONE);

                    edittype.setVisibility(View.GONE);

                    editcolor.setVisibility(View.GONE);

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
                if(parent.getItemAtPosition(position).equals("select a location")){
                    System.out.println("ami retarded");
                }
                else {
                    if(currentFilters.contains(parent.getItemAtPosition(position))){
                        Toast.makeText(Dokan.this, locationOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(Dokan.this, locationOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                        currentFilters.add(locationOptions.get(position));
                        htAdapter.update(currentFilters);
                        parent.setSelection(0);
                    }

                }
                System.out.println(currentFilters);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //TYPE ER JONNO EKHAN THEKE
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryOptions);
        // Specify layout to be used when list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        edittype.setAdapter(typeAdapter);

        edittype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("select a category")){
                    System.out.println("mammmmaaa mojaaaaa");
                }
                else {
                    if(currentFilters.contains(parent.getItemAtPosition(position))){
                        Toast.makeText(Dokan.this, categoryOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(Dokan.this, categoryOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                        currentFilters.add(categoryOptions.get(position));
                        htAdapter.update(currentFilters);
                        parent.setSelection(0);
                    }
                }
                System.out.println(currentFilters);
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
                if(parent.getItemAtPosition(position).equals("select a color")){
                    System.out.println("mammmmaaa mojaaaaa");
                }
                else {
                    if(currentFilters.contains(parent.getItemAtPosition(position))){
                        Toast.makeText(Dokan.this, colorOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(Dokan.this, colorOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                        currentFilters.add(colorOptions.get(position));
                        htAdapter.update(currentFilters);
                        parent.setSelection(0);
                    }
                }
                System.out.println(currentFilters);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.dokanProducts);
        StaggeredAdapter staggeredRecyclerViewAdapter =
                new StaggeredAdapter(mNames, mPrice, mImageURL, mID,this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}
