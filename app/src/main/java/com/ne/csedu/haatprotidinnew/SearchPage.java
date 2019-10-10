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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class SearchPage extends AppCompatActivity implements AsyncResponse{
    private static final String TAG = "SearchPage";
    private  static  final int NUM_COLUMNS=2;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mPrice=new ArrayList<>();
    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mID = new ArrayList<>();
    String Check;
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
    RecyclerView recyclerView;
    StaggeredAdapter staggeredRecyclerViewAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    String[] attributes;
    String[] values;
    String Dokantype,DokanPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
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

        final Button btnSearch = findViewById(R.id.btnSearch);

        final Intent fromHomeintent = getIntent();

        attributes = new String[5000];
        values = new String[5000];

        Dokantype = fromHomeintent.getStringExtra("dokan_type");
        DokanPhone = fromHomeintent.getStringExtra("phone_no");

        attributes[0] = "phone_no";
        attributes[1] = "dokan_type";

        values[0] = DokanPhone;
        values[1] = Dokantype;

        GetMethodHandler emptySearch = new GetMethodHandler(attributes,values,2,"https://haatprotidin.com/php_an/filter.php",context);
        try {
            emptySearch.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aste " + currentFilters);
                int locationIndex = 0;
                int colorIndex = 0;
                int categoryIndex = 0;

                attributes[0] = "phone_no";
                //values[0] = fromHomeintent.getStringExtra("phone_no");
                values[0] = "123";
                attributes[1] = "dokan_type";
                values[1] = fromHomeintent.getStringExtra("dokan_type");



                for(int i=0; i<currentFilters.size(); i++){
                    if(locationOptions.contains(currentFilters.get(i))){
                        attributes[i+2] = "location"+locationIndex;
                        values[i+2] = currentFilters.get(i);
                        locationIndex = locationIndex + 1;
                    }
                    if(colorOptions.contains(currentFilters.get(i))){
                        attributes[i+2] = "color"+colorIndex;
                        values[i+2] = currentFilters.get(i);
                        colorIndex = colorIndex + 1;
                    }
                    if(categoryOptions.contains(currentFilters.get(i))){
                        attributes[i+2] = "category"+categoryIndex;
                        values[i+2] = currentFilters.get(i);
                        categoryIndex = categoryIndex + 1;
                    }
                }
                for(int j=0; j<currentFilters.size()+2;j++){
                    System.out.println("aguuun" + attributes[j] + " " + values[j]);
                }
                GetMethodHandler productRetriever = new GetMethodHandler(attributes, values, currentFilters.size() +2, "https://haatprotidin.com/php_an/filter.php", context);
                try {
                    productRetriever.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        initRecyclerView();
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
                        Toast.makeText(SearchPage.this, locationOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(SearchPage.this, locationOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SearchPage.this, categoryOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(SearchPage.this, categoryOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SearchPage.this, colorOptions.get(position) + " Already Selected", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(parent.getItemAtPosition(position));
                        Toast.makeText(SearchPage.this, colorOptions.get(position) + " selected", Toast.LENGTH_SHORT).show();
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

         recyclerView = findViewById(R.id.pref_Recycler);
        staggeredRecyclerViewAdapter =
                new StaggeredAdapter(mNames, mPrice, mImageURL, mID,this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }

    @Override
    public void processFinish(String output) {
        try {
            mID.clear();
            mPrice.clear();
            mNames.clear();
            mImageURL.clear();
            JSONArray jsonArray= new JSONArray(output);

            int jsonlength = jsonArray.length();
            JSONObject checker = jsonArray.getJSONObject(0);
            if(checker.get("status").toString().equals("-1")){
                Toast.makeText(SearchPage.this, "Search Failed, Please try again.", Toast.LENGTH_SHORT).show();
            }else if (jsonlength == 1){
                Toast.makeText(SearchPage.this, "No such product!", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=1; i<jsonlength; i++){
                    JSONObject temp = jsonArray.getJSONObject(i);
                    mID.add(temp.get("id").toString());
                    mImageURL.add(temp.get("photo_link").toString());
                    mNames.add(temp.get("name").toString());
                    mPrice.add(temp.get("price").toString());
                }
            }
            System.out.println(mImageURL);
            for(int ind=0;ind<mImageURL.size();ind++)
                System.out.println(mImageURL.get(ind));
            System.out.println("sesh");
            staggeredRecyclerViewAdapter.notifyDataSetChanged();
            System.out.println("sesh");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
