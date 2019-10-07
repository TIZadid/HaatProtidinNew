package com.ne.csedu.haatprotidinnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class SearchPage extends AppCompatActivity {
    private static final String TAG = "SearchPage";
    private  static  final int NUM_COLUMNS=2;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mPrice=new ArrayList<>();

    ArrayList<String> locationOptions = new ArrayList<String>() {};

    ArrayList<String> categoryOptions = new ArrayList<String>() {};

    ArrayList<String> colorOptions = new ArrayList<String>() {};

    int count;
    int index,userIndex;


    ArrayList<String> currentFilters = new ArrayList<>();

    private RecyclerView htRecyclerView;
    private HorTagRecyclerViewAdapter htAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);



        //hortag start
        htRecyclerView = findViewById(R.id.hor_tag_recycler_view);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        htRecyclerView.setLayoutManager(layoutManager);

        htAdapter = new HorTagRecyclerViewAdapter(currentFilters,this);
        htRecyclerView.setAdapter(htAdapter);


        EditText edittextsearch = findViewById(R.id.etSearchtext);

        Switch Filterbutton =  findViewById(R.id.Filter);
        final Spinner editlocation = findViewById(R.id.etlocation);
        final TextView locationLabel =findViewById(R.id.tvlocation);
        final Spinner edittype = findViewById(R.id.ettype);
        final TextView typeLabel = findViewById(R.id.tvtype);
        final Spinner editcolor = findViewById(R.id.etcolor);
        final TextView colorLabel = findViewById(R.id.tvcolor);

        Intent fromHomeintent = getIntent();
        ArrayList<String> temp = new ArrayList<>();
        temp =fromHomeintent.getStringArrayListExtra("location");
        locationOptions.addAll(temp);
        System.out.println("babu" + locationOptions);
        temp = fromHomeintent.getStringArrayListExtra("category");
        categoryOptions.addAll(temp);
        System.out.println(categoryOptions);
        temp = fromHomeintent.getStringArrayListExtra("color");
        colorOptions.addAll(temp);
        System.out.println("khela " + locationOptions.size() + " " + categoryOptions.size()+ " " +colorOptions.size());
        System.out.println(currentFilters.size());

        editlocation.setVisibility(View.GONE);
        locationLabel.setVisibility(View.GONE);

        edittype.setVisibility(View.GONE);
        typeLabel.setVisibility(View.GONE);

        editcolor.setVisibility(View.GONE);
        colorLabel.setVisibility(View.GONE);



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
                        locationLabel.setVisibility(View.VISIBLE);

                        edittype.setVisibility(View.VISIBLE);
                        typeLabel.setVisibility(View.VISIBLE);

                        editcolor.setVisibility(View.VISIBLE);
                        colorLabel.setVisibility(View.VISIBLE);

                        count=1;
                }
                else if(!radioButton.isChecked()){
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
                if(parent.getItemAtPosition(position).equals("select a category")){
                    System.out.println("mammmmaaa mojaaaaa");
                }
                else {
                    System.out.println(parent.getItemAtPosition(position));
                    Toast.makeText(SearchPage.this, locationOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                    currentFilters.add(locationOptions.get(position));
                    htAdapter.update(currentFilters);
                }
                System.out.println(position);
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
                    Toast.makeText(SearchPage.this, categoryOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
                    currentFilters.add(categoryOptions.get(position));
                    htAdapter.update(currentFilters);
                }
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
                if(parent.getItemAtPosition(position).equals("select a category")){
                    System.out.println("mammmmaaa mojaaaaa");
                }
                else {
                    Toast.makeText(SearchPage.this, colorOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();

                    currentFilters.add(colorOptions.get(position));
                    htAdapter.update(currentFilters);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Hortag
initStaggeredCard();

    }
    private void initStaggeredCard(){
        Log.d(TAG, "initStagg: preparing.");


        mNames.add("Havasu Falls");
        mPrice.add("TK. 2000");

        mNames.add("Trondheim");
        mPrice.add("TK. 2000");

        mNames.add("Portugal");
        mPrice.add("TK. 2000");

        mPrice.add("TK. 2000");
        mNames.add("Rocky Mountain National Park");


        mPrice.add("TK. 2000");
        mNames.add("Mahahual");

        mPrice.add("TK. 2000");
        mNames.add("Frozen Lake");


        mPrice.add("TK. 2000");
        mNames.add("White Sands Desert");

        mPrice.add("TK. 2000");
        mNames.add("Austrailia");

        mPrice.add("TK. 2000");
        mNames.add("Washington");

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.pref_Recycler);
        StaggeredAdapter staggeredRecyclerViewAdapter =
                new StaggeredAdapter(mNames, mPrice,this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}
